package mc.apptoeat.com.bot.bots;

import java.util.ArrayList;
import mc.apptoeat.com.bot.main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BotManager {
  public ArrayList<Bot> getBots() {
    return this.bots;
  }
  
  public ArrayList<Bot> getBotsTick() {
    return this.botsTick;
  }
  
  public BukkitTask getTask() {
    return this.task;
  }
  
  private final ArrayList<Bot> bots = new ArrayList<>();
  
  private final ArrayList<Bot> botsTick = new ArrayList<>();
  
  private final BukkitTask task = (new BukkitRunnable() {
      public void run() {
        BotManager.this.botsTick.clear();
        BotManager.this.botsTick.addAll(BotManager.this.bots);
        BotManager.this.botsTick.forEach(bot -> {
              if (bot.getMovements().getLocation().getWorld().equals(bot.getMovements().getTargetLocate().getTarget().getWorld())) {
                bot.tick();
                return;
              } 
              bot.silentKill();
            });
      }
    }).runTaskTimer((Plugin)main.getInstance(), 1L, 1L);
  
  public void addBot(Bot bot) {
    this.bots.add(bot);
  }
  
  public void removeBot(Bot bot) {
    this.bots.remove(bot);
  }
  
  public Bot getNpcFromId(int id) {
    for (Bot npc : this.bots) {
      if (npc.getNpc().getBukkitEntity().getEntityId() == id)
        return npc; 
    } 
    return null;
  }
  
  public Bot getNpcFromTarget(Player target) {
    for (Bot npc : this.bots) {
      if (npc.getMovements().getTargetLocate().getTargetEntity().getUniqueId() == target.getUniqueId())
        return npc; 
    } 
    return null;
  }
  
  public Bot getNpcFromTargetView(Player target) {
    for (Bot npc : this.bots) {
      if (npc.getViewsManager().getPlayers().contains(target))
        return npc; 
    } 
    return null;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\BotManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */