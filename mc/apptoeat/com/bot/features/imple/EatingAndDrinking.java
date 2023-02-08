package mc.apptoeat.com.bot.features.imple;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotHealthChangeEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class EatingAndDrinking extends Feature {
  private int runningTicks = 40;
  
  public void listen(Event event) {
    final Bot bot = event.bot;
    if (event instanceof mc.apptoeat.com.bot.botevents.events.SpawnEvent)
      (new BukkitRunnable() {
          public void run() {
            EatingAndDrinking.this.runEat(bot);
          }
        }).runTaskLater((Plugin)main.getInstance(), 1L); 
    boolean lowHealth = false;
    if (event instanceof BotHealthChangeEvent) {
      BotHealthChangeEvent e = (BotHealthChangeEvent)event;
      if (e.getAfter() <= bot.getSettingManager().getEatingSettings().getEatingGappleHealth().getIntValue())
        lowHealth = true; 
    } 
    if (event instanceof mc.apptoeat.com.bot.botevents.events.FinishEatingEvent || event instanceof mc.apptoeat.com.bot.botevents.events.EffectEndEvent || event instanceof mc.apptoeat.com.bot.botevents.events.BotRequiredToEatEvent || lowHealth) {
      boolean able = (ableToEat(bot.getSettingManager().getEatingSettings().getStartEatingDistance().getDoubleValue(), bot) || bot.getFleeManager().getTicks() > bot.getSettingManager().getEatingSettings().getStartEatingTimer().getIntValue());
      boolean needsHealth = (bot.getHealthManager().getHealth() < bot.getSettingManager().getEatingSettings().getEatingGappleHealth().getIntValue() && bot.getInventoryManager().hasItem(322).size() > 0);
      if (needsHealth)
        if (able) {
          eatGap(bot);
        } else {
          bot.getInventoryManager().setRequiredToEat(true);
        }  
    } 
    if (event instanceof mc.apptoeat.com.bot.botevents.events.FinishEatingEvent || event instanceof mc.apptoeat.com.bot.botevents.events.EffectEndEvent || event instanceof mc.apptoeat.com.bot.botevents.events.BotRequiredToEatEvent) {
      boolean able = (ableToEat(bot.getSettingManager().getEatingSettings().getStartEatingDistance().getDoubleValue(), bot) || bot.getFleeManager().getTicks() > bot.getSettingManager().getEatingSettings().getStartEatingTimer().getIntValue());
      if (needsToDrink(bot))
        if (able) {
          runEat(bot);
        } else {
          bot.getInventoryManager().setRequiredToEat(true);
        }  
    } 
    if (event instanceof mc.apptoeat.com.bot.botevents.events.EatingCancelEvent)
      bot.getInventoryManager().setRequiredToEat(true); 
  }
  
  public boolean needsToDrink(Bot bot) {
    List<ItemStack> list = bot.getInventoryManager().hasItem(373);
    AtomicBoolean result = new AtomicBoolean(false);
    list.forEach(item -> {
          Collection<PotionEffect> effects = Potion.fromItemStack(item).getEffects();
          if (Potion.fromItemStack(item).isSplash())
            return; 
          if (bot.getEffectsManager().hasEffects(effects))
            return; 
          result.set(true);
        });
    return result.get();
  }
  
  public void runEat(Bot bot) {
    bot.getInventoryManager().allowEating();
    List<ItemStack> list = bot.getInventoryManager().hasItem(373);
    list.forEach(itemStack -> bot.getInventoryManager().eat(itemStack));
  }
  
  public static void eatGap(Bot bot) {
    bot.getInventoryManager().allowEating();
    List<ItemStack> list = bot.getInventoryManager().hasItem(322);
    list.forEach(itemStack -> bot.getInventoryManager().eat(itemStack));
  }
  
  public void tickEvent(Bot bot) {
    boolean able = ableToEat(bot.getSettingManager().getEatingSettings().getCancelEatingDistance().getDoubleValue(), bot);
    if (!able && bot.getInventoryManager().isEating())
      bot.getMovements().setJump(true); 
  }
  
  public boolean ableToEat(double dist, Bot bot) {
    if (!bot.getMovements().getTargetLocate().getTargetEntity().getWorld().equals(bot.getMovements().getLocation().getWorld()))
      return true; 
    return (bot.getMovements().getTargetLocate().getTargetEntity().getLocation().distance(bot.getMovements().getLocation()) > dist);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\EatingAndDrinking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */