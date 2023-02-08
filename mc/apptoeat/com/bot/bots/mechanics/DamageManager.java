package mc.apptoeat.com.bot.bots.mechanics;

import java.io.IOException;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotDamageEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.FileManager;
import mc.apptoeat.com.bot.utils.Nms;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class DamageManager extends Manager {
  private int damageTicks;
  
  private long last;
  
  private boolean noDamage;
  
  private boolean combo;
  
  private boolean allowGlobalAttacks;
  
  public void setDamageTicks(int damageTicks) {
    this.damageTicks = damageTicks;
  }
  
  public void setLast(long last) {
    this.last = last;
  }
  
  public void setNoDamage(boolean noDamage) {
    this.noDamage = noDamage;
  }
  
  public void setCombo(boolean combo) {
    this.combo = combo;
  }
  
  public void setAllowGlobalAttacks(boolean allowGlobalAttacks) {
    this.allowGlobalAttacks = allowGlobalAttacks;
  }
  
  public DamageManager(Bot bot) {
    super(bot);
  }
  
  public int getDamageTicks() {
    return this.damageTicks;
  }
  
  public long getLast() {
    return this.last;
  }
  
  public boolean isNoDamage() {
    return this.noDamage;
  }
  
  public boolean isCombo() {
    return this.combo;
  }
  
  public boolean isAllowGlobalAttacks() {
    return this.allowGlobalAttacks;
  }
  
  public void tickPre() {
    this.damageTicks++;
  }
  
  public void damage(DamageSource source, float damage) {
    long attackDelay = 475L;
    if (this.combo)
      attackDelay = 75L; 
    if (System.currentTimeMillis() - this.last <= attackDelay)
      return; 
    callEvent((Event)new BotDamageEvent(this.bot));
    this.last = System.currentTimeMillis();
    this.damageTicks = 0;
    if (source.i().getBukkitEntity() instanceof Player) {
      Player player = (Player)source.i().getBukkitEntity();
      playerAttack(player, damage);
    } else if (source.i().getBukkitEntity() instanceof Projectile) {
      Projectile projectile = (Projectile)source.i().getBukkitEntity();
      projectileAttack(projectile, damage, source);
    } 
  }
  
  public void projectileAttack(Projectile projectile, float damage, DamageSource source) {
    Vector direction = projectile.getVelocity().setY(0).normalize();
    double v = 0.35D;
    double h = 0.6D;
    direction.multiply(h).setY(v);
    this.bot.getVelocityCalculator().setVelocity(direction);
    this.bot.getDamageCalculator().damage(source, damage);
    damageEffect();
    if (projectile.getType().equals(EntityType.ARROW)) {
      playSound();
      Player attacker = (Player)projectile.getShooter();
      playProjectileSound(attacker);
      String health = (int)(this.bot.getHealthManager().getHealth() / 2.0D) + "❤";
      attacker.sendMessage(Color.code("&cBot &fis now at &c" + health + "&f."));
    } else {
      playSound();
    } 
    if (main.getInstance().getBukkitEvents().isProjectileHitEvent())
      Bukkit.getServer().getPluginManager().callEvent((Event)new ProjectileHitEvent(projectile)); 
  }
  
  public void playerAttack(Player attacker, float damage) {
    if (!attacker.getUniqueId().equals(this.bot.getMovements().getTargetLocate().getTargetEntity().getUniqueId()) && !this.allowGlobalAttacks)
      return; 
    velocity(attacker);
    damageEffect();
    playSound();
    sprintReset(attacker);
    if (!this.noDamage)
      removeHealth(damage, attacker); 
  }
  
  public void removeHealth(Player attacker) {
    float swordAttackDamage = (float)Nms.getPlayer(attacker).getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
    float sharp = attacker.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
    swordAttackDamage = (float)(swordAttackDamage + sharp * 1.25D);
    this.bot.getDamageCalculator().damage(DamageSource.playerAttack((EntityHuman)Nms.getPlayer(attacker)), swordAttackDamage);
  }
  
  public void removeHealth(float damage, Player attacker) {
    this.bot.getDamageCalculator().damage(DamageSource.playerAttack((EntityHuman)Nms.getPlayer(attacker)), damage);
  }
  
  public void sprintReset(Player attacker) {
    if (Nms.getPlayer(attacker).isSprinting())
      Nms.getPlayer(attacker).setSprinting(false); 
  }
  
  public void playSwingEffect(Player attacker) {
    int type = 4;
    if (Nms.getPlayer(attacker).isSprinting() || !FileManager.getOrDefault((JavaPlugin)main.getInstance(), "serversettings.changeParticleDependsOnSprint", false))
      type = 5; 
    int finalType = type;
    globalAction(player -> Nms.sendPacket(player, (Packet)new PacketPlayOutAnimation((Entity)this.bot.getNpc(), finalType)));
  }
  
  public void damageEffect() {
    globalAction(player -> Nms.sendPacket(player, (Packet)new PacketPlayOutAnimation((Entity)this.bot.getNpc(), 1)));
  }
  
  public void velocity(Player attacker) {
    this.bot.getVelocityCalculator().attack(Nms.getPlayer(attacker));
    boolean sprint = Nms.getPlayer(attacker).isSprinting();
    this.bot.getVelocityCalculator().setSprinting(sprint);
  }
  
  public void playProjectileSound(Player shooter) {
    shooter.playSound(this.bot.getMovements().getLocation(), Sound.ORB_PICKUP, 20.0F, 0.5F);
  }
  
  public void playSound() {
    globalAction(player -> player.playSound(this.bot.getMovements().getLocation(), Sound.HURT_FLESH, 100.0F, 1.0F));
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\DamageManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */