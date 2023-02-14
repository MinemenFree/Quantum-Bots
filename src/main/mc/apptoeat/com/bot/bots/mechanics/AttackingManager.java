package mc.apptoeat.com.bot.bots.mechanics;

import java.io.IOException;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotAttackEvent;
import mc.apptoeat.com.bot.botevents.events.BotAttackLandEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements.TargetLocate;
import mc.apptoeat.com.bot.bots.setting.AttackSettings;
import mc.apptoeat.com.bot.utils.Nms;
import mc.apptoeat.com.bot.utils.objects.AABB;
import mc.apptoeat.com.bot.utils.raycast.RayCasting;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class AttackingManager extends Manager {
  private boolean attacking;
  
  private boolean blocking;
  
  private int tickCounter;
  
  private long attacked;
  
  private boolean landAttack;
  
  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
  }
  
  public void setBlocking(boolean blocking) {
    this.blocking = blocking;
  }
  
  public void setTickCounter(int tickCounter) {
    this.tickCounter = tickCounter;
  }
  
  public void setAttacked(long attacked) {
    this.attacked = attacked;
  }
  
  public void setLandAttack(boolean landAttack) {
    this.landAttack = landAttack;
  }
  
  public AttackingManager(Bot bot) {
    super(bot);
    this.attacking = true;
    this.blocking = false;
  }
  
  public boolean isAttacking() {
    return this.attacking;
  }
  
  public boolean isBlocking() {
    return this.blocking;
  }
  
  public int getTickCounter() {
    return this.tickCounter;
  }
  
  public long getAttacked() {
    return this.attacked;
  }
  
  public boolean isLandAttack() {
    return this.landAttack;
  }
  
  public void tickPre() {
    if (!this.landAttack)
      return; 
    this.landAttack = false;
    callEvent((Event)new BotAttackLandEvent(this.bot, this.bot.getMovements().getTargetLocate().getTargetEntity()));
  }
  
  public void tickPost() {
    if (!this.attacking)
      return; 
    if (this.bot.getFleeManager().isActive())
      return; 
    TargetLocate targetLocate = this.bot.getMovements().getTargetLocate();
    AttackSettings clickSetting = this.bot.getSettingManager().getClickSetting();
    int delay = clickSetting.getDelay();
    int clicks = 0;
    this.tickCounter += 50;
    while (this.tickCounter > delay) {
      this.tickCounter -= delay;
      clicks++;
    } 
    for (int i = 0; i < clicks; i++) {
      double dist = getDistance();
      if (dist == -1.0D)
        return; 
      if (dist >= this.bot.getSettingManager().getClickSetting().getSwingRange().getDoubleValue())
        return; 
      swing();
      if (!rayTrace())
        return; 
      attack((Entity)targetLocate.getTargetEntity());
    } 
  }
  
  public boolean rayTrace() {
    Location loc = this.bot.getMovements().getLocation().clone();
    Vector from = loc.toVector();
    double eyeHeight = 1.62D;
    loc.setY(loc.getY() + eyeHeight);
    loc.add(loc.getDirection().normalize().multiply(-0.05D));
    double offSet = this.bot.getMovements().getCollision().getXzHitBoxOffset();
    Vector to = from.clone().add(loc.getDirection().normalize().multiply(this.bot.getSettingManager().getClickSetting().getReach().getDoubleValue() + 0.05D));
    AABB collision = getHitBox(this.bot.getLagCompensatorManager().getClientLocation(), (float)offSet);
    Vector bc = traceWithBlocks(loc.toVector(), to.toLocation(loc.getWorld()), 0.5D);
    double dist = 100.0D;
    if (bc != null)
      dist = bc.distance(loc.toVector()); 
    if (dist <= 0.3D)
      dist = 0.3D; 
    Vector result = RayCasting.hitboxIntersection(loc.toVector(), to, collision);
    if (result == null)
      return false; 
    double reach = result.distance(from);
    boolean attack = (dist > reach);
    return (reach <= this.bot.getSettingManager().getClickSetting().getReach().getDoubleValue() && attack);
  }
  
  public Vector traceWithBlocks(Vector from, Location to, double accurate) {
    Vector dir = to.toVector().subtract(from).normalize();
    double reach = to.toVector().distance(from);
    World world = to.getWorld();
    Vector lastResult = from;
    double i;
    for (i = 0.0D; i < reach; i += accurate) {
      Vector newPos = from.clone().add(dir.clone().multiply(i));
      if (newPos.toLocation(world).getBlock().getType().isSolid())
        return lastResult; 
      lastResult = newPos;
    } 
    return null;
  }
  
  public AABB getHitBox(Location location, float offSet) {
    Vector targetPos = location.toVector();
    return new AABB(new Vector(targetPos.getX() - offSet, targetPos.getY(), targetPos.getZ() - offSet), new Vector(targetPos.getX() + offSet, targetPos.getY() + 2.0D, targetPos.getZ() + offSet));
  }
  
  public void swing() {
    globalAction(player -> Nms.sendPacket(player, (Packet)new PacketPlayOutAnimation((Entity)this.bot.getNpc(), 0)));
  }
  
  double calculateItemAttackDamage(ItemStack item) {
    if (item == null)
      return 1.0D; 
    double attackDamage = 1.0D;
    if (item.getType().equals(Material.DIAMOND_SWORD)) {
      attackDamage += 7.0D;
    } else if (item.getType().equals(Material.IRON_SWORD)) {
      attackDamage += 6.0D;
    } else if (item.getType().equals(Material.STONE_SWORD)) {
      attackDamage += 5.0D;
    } else if (item.getType().equals(Material.GOLD_SWORD)) {
      attackDamage += 4.0D;
    } else if (item.getType().equals(Material.WOOD_SWORD)) {
      attackDamage += 4.0D;
    } else if (item.getType().equals(Material.DIAMOND_AXE)) {
      attackDamage += 6.0D;
    } else if (item.getType().equals(Material.IRON_AXE)) {
      attackDamage += 5.0D;
    } else if (item.getType().equals(Material.STONE_AXE)) {
      attackDamage += 4.0D;
    } else if (item.getType().equals(Material.GOLD_AXE)) {
      attackDamage += 3.0D;
    } else if (item.getType().equals(Material.WOOD_AXE)) {
      attackDamage += 3.0D;
    } else if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
      attackDamage += 5.0D;
    } else if (item.getType().equals(Material.IRON_PICKAXE)) {
      attackDamage += 4.0D;
    } else if (item.getType().equals(Material.STONE_PICKAXE)) {
      attackDamage += 3.0D;
    } else if (item.getType().equals(Material.GOLD_PICKAXE)) {
      attackDamage += 2.0D;
    } else if (item.getType().equals(Material.WOOD_PICKAXE)) {
      attackDamage += 2.0D;
    } else if (item.getType().equals(Material.DIAMOND_SPADE)) {
      attackDamage = 5.5D;
    } else if (item.getType().equals(Material.IRON_SPADE)) {
      attackDamage = 4.5D;
    } else if (item.getType().equals(Material.STONE_SPADE)) {
      attackDamage = 3.5D;
    } else if (item.getType().equals(Material.GOLD_SPADE)) {
      attackDamage = 2.5D;
    } else if (item.getType().equals(Material.WOOD_SPADE)) {
      attackDamage = 2.5D;
    } 
    int strenghteffectLVL = this.bot.getEffectsManager().getLevel(PotionEffectType.INCREASE_DAMAGE);
    attackDamage += (3 * strenghteffectLVL);
    int sharpnessLVL = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
    attackDamage += sharpnessLVL * 1.25D;
    return attackDamage;
  }
  
  public void attack(Entity player) {
    this.bot.getMovements().attack();
    int damage = (int)calculateItemAttackDamage(this.bot.getInventoryManager().getItemInMainHand());
    ((Damageable)player).damage(damage, (Entity)this.bot.getNpc().getBukkitEntity());
    callEvent((Event)new BotAttackEvent(this.bot));
    this.attacked = System.currentTimeMillis();
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\AttackingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */