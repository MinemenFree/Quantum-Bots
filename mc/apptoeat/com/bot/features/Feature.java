package mc.apptoeat.com.bot.features;

import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.FinalMoveEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Feature extends BotEvent implements Listener {
  public Feature() {
    main.getInstance().getEventManager().registerEvent(this);
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  public void listen(Event event) {
    if (event instanceof FinalMoveEvent) {
      FinalMoveEvent e = (FinalMoveEvent)event;
      finalMoveEvent(e, event.bot, event.bot.getSettingManager());
    } 
  }
  
  public void finalMoveEvent(FinalMoveEvent event, Bot bot, SettingManager setting) {}
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\Feature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */