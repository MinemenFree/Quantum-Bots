package mc.apptoeat.com.bot.utils.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class GlobalGui extends Gui {
  private Runnable update;
  
  public GlobalGui(String title, int size) {
    super(title, size);
  }
  
  public void openGui(Player player) {
    player.closeInventory();
    this.update.run();
    openInventory((HumanEntity)player);
  }
  
  public void minOpen(Player player) {
    this.update.run();
    openInventory((HumanEntity)player);
  }
  
  public void setGui(Runnable update) {
    this.update = update;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\gui\GlobalGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */