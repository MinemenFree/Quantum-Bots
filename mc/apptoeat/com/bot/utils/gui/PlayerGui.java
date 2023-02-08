package mc.apptoeat.com.bot.utils.gui;

import mc.apptoeat.com.bot.data.DataPlayer;
import org.bukkit.entity.HumanEntity;

public class PlayerGui extends Gui {
  private Runnable update;
  
  private final DataPlayer data;
  
  public Runnable getUpdate() {
    return this.update;
  }
  
  public DataPlayer getData() {
    return this.data;
  }
  
  public PlayerGui(String title, int size, DataPlayer data) {
    super(title, size);
    this.data = data;
  }
  
  public void openGui() {
    this.data.getPlayer().closeInventory();
    this.update.run();
    openInventory((HumanEntity)this.data.getPlayer());
  }
  
  public void minOpen() {
    this.update.run();
    openInventory((HumanEntity)this.data.getPlayer());
  }
  
  public void setGui(Runnable update) {
    this.update = update;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\gui\PlayerGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */