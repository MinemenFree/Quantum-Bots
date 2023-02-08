package mc.apptoeat.com.bot.bots.mechanics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.EffectEndEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.main;
import net.minecraft.server.v1_8_R3.MobEffect;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectsManager extends Manager {
  private final Map<PotionEffectType, Integer> effects;
  
  private final Bot bot;
  
  public Map<PotionEffectType, Integer> getEffects() {
    return this.effects;
  }
  
  public Bot getBot() {
    return this.bot;
  }
  
  public EffectsManager(Bot bot) {
    super(bot);
    this.effects = new HashMap<>();
    this.bot = bot;
  }
  
  public void tickPre() {
    (this.bot.getNpc()).effects.clear();
    this.effects.keySet().forEach(effect -> this.bot.getNpc().addEffect(new MobEffect(effect.getId(), 100, ((Integer)this.effects.get(effect)).intValue(), true, true)));
  }
  
  public void addEffect(PotionEffectType type, Integer level) {
    this.effects.put(type, level);
    addEffectEvent(type, level.intValue());
  }
  
  public void removeEffect(PotionEffectType type) {
    this.effects.put(type, Integer.valueOf(0));
    this.bot.getMovements().callEvent((Event)new EffectEndEvent(this.bot, type));
  }
  
  public void addEffect(final PotionEffectType type, Integer level, int time) {
    this.effects.put(type, level);
    (new BukkitRunnable() {
        public void run() {
          if (!EffectsManager.this.bot.isAlive())
            return; 
          EffectsManager.this.removeEffect(type);
        }
      }).runTaskLater((Plugin)main.getInstance(), (time / 20));
    addEffectEvent(type, level.intValue());
  }
  
  public int getLevel(PotionEffectType type) {
    if (!this.effects.containsKey(type))
      return 0; 
    return ((Integer)this.effects.get(type)).intValue();
  }
  
  public void addEffects(Collection<PotionEffect> effects) {
    for (PotionEffect effect : effects)
      addEffect(effect.getType(), Integer.valueOf(effect.getAmplifier() + 1), effect.getDuration() * 20); 
  }
  
  public boolean hasEffects(Collection<PotionEffect> effects) {
    for (PotionEffect effect : effects) {
      if (!this.effects.containsKey(effect.getType()))
        return false; 
      if (((Integer)this.effects.get(effect.getType())).intValue() == 0)
        return false; 
    } 
    return true;
  }
  
  public void addEffectEvent(PotionEffectType type, int level) {
    if (type == PotionEffectType.ABSORPTION) {
      int extraHealth = 4 * level;
      this.bot.getHealthManager().setAbsHearts(extraHealth);
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\EffectsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */