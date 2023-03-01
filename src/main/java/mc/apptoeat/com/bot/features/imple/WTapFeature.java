package mc.apptoeat.com.bot.features.imple;

import java.util.Random;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotAttackLandEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.WTapSetting;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WTapFeature extends Feature {
  public void listen(Event event) {
    if (event instanceof BotAttackLandEvent) {
      final long tapLong;
      BotAttackLandEvent e = (BotAttackLandEvent)event;
      final Bot bot = e.bot;
      Random r = new Random();
      int random = r.nextInt(100);
      WTapSetting wTapSetting = bot.getSettingManager().getWTapSetting();
      bot.getMovements().setAttackSprint(false);
      if (!wTapSetting.getEnabled().isBooleanValue())
        return; 
      if (random > wTapSetting.getChance().getIntValue())
        return; 
      if (main.getInstance().getDataManager().getPlayer(e.getPlayer()).getBotCombo() > 3) {
        tapLong = wTapSetting.getComboTapLong().getIntValue();
      } else {
        tapLong = wTapSetting.getTapLong().getIntValue();
      } 
      (new BukkitRunnable() {
          public void run() {
            if (!bot.isAlive())
              return; 
            if (bot.getSettingManager().getWTapSetting().getBlockingHitting().isBooleanValue()) {
              bot.getPacketsManager().sendBlockHittingPackets(true);
              bot.getMovements().setUse(true);
            } else {
              bot.getMovements().setMove(false);
            } 
            (new BukkitRunnable() {
                public void run() {
                  if (!bot.isAlive())
                    return; 
                  if (bot.getSettingManager().getWTapSetting().getBlockingHitting().isBooleanValue()) {
                    bot.getPacketsManager().sendBlockHittingPackets(false);
                    bot.getMovements().setUse(false);
                  } else {
                    bot.getMovements().setMove(true);
                  } 
                }
              }).runTaskLater((Plugin)main.getInstance(), tapLong);
          }
        }).runTaskLater((Plugin)main.getInstance(), wTapSetting.getTapStart().getIntValue());
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\WTapFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */