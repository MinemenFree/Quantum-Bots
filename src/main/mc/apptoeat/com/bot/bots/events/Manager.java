package mc.apptoeat.com.bot.bots.events;

import java.io.IOException;
import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.objects.PlayerRunnable;
import org.bukkit.entity.Player;

public class Manager {
  public SettingManager settingManager;
  
  public final Bot bot;
  
  public SettingManager getSettingManager() {
    return this.settingManager;
  }
  
  public Bot getBot() {
    return this.bot;
  }
  
  public Manager(Bot bot) {
    this.bot = bot;
    this.settingManager = bot.getSettingManager();
  }
  
  public void globalAction(PlayerRunnable runnable) {
    getBot().getViewsManager().getPlayers().forEach(player -> {
          try {
            runnable.run(player);
          } catch (IOException e) {
            e.printStackTrace();
          } 
        });
  }
  
  public void debug(String string) {
    getBot().getViewsManager().getPlayers().forEach(player -> player.sendMessage(Color.code("&3Debug&7: " + string)));
  }
  
  public void addPlayerToList(Player player) {}
  
  public void callEvent(Event event) {
    for (BotEvent events : main.getInstance().getEventManager().getEvents())
      events.call(event); 
  }
  
  public void tickPre() {}
  
  public void tickPost() {}
  
  public void tickMid() {}
  
  public double getDistance() {
    if (!this.bot.getMovements().getTargetLocate().getTargetEntity().getLocation().getWorld().equals(this.bot.getMovements().getLocation().getWorld()))
      return -1.0D; 
    return this.bot.getMovements().getTargetLocate().getTargetEntity().getLocation().distance(this.bot.getMovements().getLocation());
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\events\Manager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */