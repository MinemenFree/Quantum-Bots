package mc.apptoeat.com.bot.features.imple;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.FleeSettings;
import mc.apptoeat.com.bot.bots.setting.StrafeSettings;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class StrafeFeature extends Feature {
  private final Map<Bot, Float> strafe = new HashMap<>();
  
  private final Map<Bot, Integer> ticks = new HashMap<>();
  
  private final Map<Bot, Integer> combo = new HashMap<>();
  
  public void tickEvent(Bot bot) {
    StrafeSettings strafeSettings = bot.getSettingManager().getStrafeSettings();
    boolean inCombo = (((Integer)this.combo.getOrDefault(bot, (V)Integer.valueOf(0))).intValue() > 3);
    boolean close = (bot.getMovements().getLocation().distance(bot.getMovements().getTargetLocate().getTargetEntity().getLocation()) < 6.0D);
    boolean chance = ((new Random()).nextInt(100) <= bot.getSettingManager().getStrafeSettings().getChance().getIntValue());
    if (!strafeSettings.getEnabled().isBooleanValue() || (inCombo && !strafeSettings.getStrafeInCombo().isBooleanValue()) || !close) {
      this.strafe.put(bot, Float.valueOf(0.0F));
      bot.getMovements().setStrafe(0.0F);
      return;
    } 
    if (!chance) {
      bot.getMovements().setStrafe(0.0F);
      return;
    } 
    if (((Float)this.strafe.getOrDefault(bot, Float.valueOf(0.0F))).floatValue() == 0.0F)
      this.strafe.put(bot, Float.valueOf(45.0F)); 
    this.ticks.put(bot, Integer.valueOf(((Integer)this.ticks.getOrDefault(bot, Integer.valueOf(0))).intValue() + 1));
    if (((Integer)this.ticks.get(bot)).intValue() > strafeSettings.getSwitchSideTicks().getIntValue()) {
      this.ticks.put(bot, Integer.valueOf(0));
      this.strafe.put(bot, Float.valueOf(((Float)this.strafe.getOrDefault(bot, Float.valueOf(0.0F))).floatValue() * -1.0F));
      bot.getMovements().setSpeedMultiplier(0.9523809523809523D);
    } 
    bot.getMovements().setStrafe(((Float)this.strafe.get(bot)).floatValue());
  }
  
  public void listen(final Event event) {
    if (event instanceof mc.apptoeat.com.bot.botevents.events.BotDamageEvent) {
      this.combo.put(event.bot, Integer.valueOf(((Integer)this.combo.getOrDefault(event.bot, Integer.valueOf(0))).intValue() + 1));
      FleeSettings fleeSettings = event.bot.getSettingManager().getFleeSettings();
      if (((Integer)this.combo.get(event.bot)).intValue() > fleeSettings.getRunFromCombosHits().getIntValue() && fleeSettings.getRunFromCombos().isBooleanValue() && !event.bot.getFleeManager().isRunning()) {
        event.bot.getFleeManager().setRunningFromCombo(true);
        this.combo.put(event.bot, Integer.valueOf(0));
        (new BukkitRunnable() {
            public void run() {
              event.bot.getFleeManager().setRunningFromCombo(false);
            }
          }).runTaskLater((Plugin)main.getInstance(), fleeSettings.getRunFromCombosTime().getIntValue());
      } 
    } 
    if (event instanceof mc.apptoeat.com.bot.botevents.events.BotAttackEvent)
      this.combo.put(event.bot, Integer.valueOf(0)); 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\StrafeFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */