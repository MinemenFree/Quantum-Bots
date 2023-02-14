package mc.apptoeat.com.bot.features.imple;

import java.util.List;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotArmorBreakEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import org.bukkit.inventory.ItemStack;

public class ArmorEquipFeature extends Feature {
  public void listen(Event event) {
    if (event instanceof BotArmorBreakEvent) {
      BotArmorBreakEvent e = (BotArmorBreakEvent)event;
      int index = e.getArmorSlot();
      Bot bot = e.bot;
      List<ItemStack> stack = bot.getInventoryManager().hasArmor(index);
      if (stack.isEmpty())
        return; 
      bot.getInventoryManager().addArmor(index, stack.get(0));
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\ArmorEquipFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */