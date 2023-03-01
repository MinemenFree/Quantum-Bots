package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PreGap extends Feature {
  public void listen(Event event) {
    if (event instanceof mc.apptoeat.com.bot.botevents.events.SpawnEvent) {
      final Bot bot = event.bot;
      (new BukkitRunnable() {
          public void run() {
            boolean a = (bot.getInventoryManager().hasItem(322).size() > 0);
            boolean b = bot.getSettingManager().getEatingSettings().getPreGap().isBooleanValue();
            Bukkit.broadcastMessage("a " + a + " b " + b);
            if (a && b) {
              Bukkit.broadcastMessage("pre Gap");
              EatingAndDrinking.eatGap(bot);
            } 
          }
        }).runTaskLater((Plugin)main.getInstance(), 120L);
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\PreGap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */