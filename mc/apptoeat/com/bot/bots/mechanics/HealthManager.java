package mc.apptoeat.com.bot.bots.mechanics;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotHealthChangeEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffectType;

public class HealthManager extends Manager {
  private double health;
  
  private int maxHeath;
  
  private int naturalRegTicks;
  
  private int requiredNaturalRegTicks;
  
  private float absHearts;
  
  private int regenTicks;
  
  private int fireTicks;
  
  public void setHealth(double health) {
    this.health = health;
  }
  
  public void setMaxHeath(int maxHeath) {
    this.maxHeath = maxHeath;
  }
  
  public void setNaturalRegTicks(int naturalRegTicks) {
    this.naturalRegTicks = naturalRegTicks;
  }
  
  public void setRequiredNaturalRegTicks(int requiredNaturalRegTicks) {
    this.requiredNaturalRegTicks = requiredNaturalRegTicks;
  }
  
  public void setAbsHearts(float absHearts) {
    this.absHearts = absHearts;
  }
  
  public void setRegenTicks(int regenTicks) {
    this.regenTicks = regenTicks;
  }
  
  public void setFireTicks(int fireTicks) {
    this.fireTicks = fireTicks;
  }
  
  public HealthManager(Bot bot) {
    super(bot);
    this.maxHeath = 20;
    this.requiredNaturalRegTicks = 20;
    this.fireTicks = 0;
    this.health = this.maxHeath;
  }
  
  public double getHealth() {
    return this.health;
  }
  
  public int getMaxHeath() {
    return this.maxHeath;
  }
  
  public int getNaturalRegTicks() {
    return this.naturalRegTicks;
  }
  
  public int getRequiredNaturalRegTicks() {
    return this.requiredNaturalRegTicks;
  }
  
  public float getAbsHearts() {
    return this.absHearts;
  }
  
  public int getRegenTicks() {
    return this.regenTicks;
  }
  
  public int getFireTicks() {
    return this.fireTicks;
  }
  
  public void tickPre() {
    if (!this.bot.getNpc().isAlive()) {
      deathEvent();
      Bukkit.broadcastMessage("d2");
      return;
    } 
    this.bot.getNpc().setHealth((float)this.health);
    int regenLevel = this.bot.getEffectsManager().getLevel(PotionEffectType.REGENERATION);
    regenerationCheck(regenLevel);
    absCheck();
    fireCheck();
  }
  
  public void absCheck() {
    if (this.bot.getEffectsManager().getLevel(PotionEffectType.ABSORPTION) == 0)
      this.absHearts = 0.0F; 
  }
  
  public void regenerationCheck(int level) {
    if (level == 0) {
      this.regenTicks = 0;
      return;
    } 
    int requireTicks = 50;
    if (level == 2) {
      requireTicks = 25;
    } else if (level == 3) {
      requireTicks = 12;
    } else if (level == 4) {
      requireTicks = 6;
    } else if (level == 5) {
      requireTicks = 3;
    } else if (level >= 6) {
      requireTicks = 1;
    } 
    if (this.regenTicks++ != 0 && this.regenTicks % requireTicks == 0)
      addHealth(1.0D); 
  }
  
  public void fireCheck() {
    int fireRess = this.bot.getEffectsManager().getLevel(PotionEffectType.FIRE_RESISTANCE);
    this.fireTicks = Math.max(0, this.fireTicks - 1);
    if (fireRess >= 1 || this.fireTicks <= 0)
      return; 
    if (this.fireTicks % 10 == 0)
      removeHealth(1.0D); 
  }
  
  public void removeHealth(double amount) {
    healthChangeEvent(this.health, this.health - amount);
    this.health -= amount;
    if (this.health < 0.0D) {
      deathEvent();
      Bukkit.broadcastMessage("d1");
    } 
  }
  
  public void deathEvent() {
    this.bot.kill();
  }
  
  public void healthChangeEvent(double before, double after) {
    callEvent((Event)new BotHealthChangeEvent(this.bot, after, before));
  }
  
  public void addHealth(double amount) {
    amount = Math.max(this.health + amount, 20.0D) - this.health;
    healthChangeEvent(this.health, Math.min(this.maxHeath, this.health + amount));
    this.health = Math.min(this.maxHeath, this.health + amount);
  }
  
  public void fire(int ticks) {
    this.fireTicks = ticks;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\HealthManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */