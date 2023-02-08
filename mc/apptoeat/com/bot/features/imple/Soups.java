package mc.apptoeat.com.bot.features.imple;

import java.util.List;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotHealthChangeEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.mechanics.InventoryManager;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Soups extends Feature {
  private int soupTime = 5;
  
  private int soupHealth = 10;
  
  public void listen(Event event) {
    if (event instanceof BotHealthChangeEvent) {
      BotHealthChangeEvent e = (BotHealthChangeEvent)event;
      if (e.getAfter() <= this.soupHealth) {
        final Bot bot = e.bot;
        final InventoryManager manager = bot.getInventoryManager();
        List<ItemStack> soups = manager.hasItem(Material.MUSHROOM_SOUP);
        if (soups.size() <= 0)
          return; 
        ItemStack soup = soups.get(0);
        final int slot = manager.getItemSlot(soup);
        manager.setMainHand(slot);
        (new BukkitRunnable() {
            public void run() {
              if (!bot.isAlive())
                return; 
              manager.setMainHand(1);
              bot.getHealthManager().addHealth(6.0D);
              manager.removeItem(slot);
            }
          }).runTaskLater((Plugin)main.getInstance(), this.soupTime);
      } 
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\Soups.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */