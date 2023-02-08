package mc.apptoeat.com.bot.utils.gui.imple;

import java.io.IOException;
import java.util.HashMap;
import mc.apptoeat.com.bot.bots.setting.Setting;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.bots.setting.SettingObject;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.MathUtils;
import mc.apptoeat.com.bot.utils.gui.GlobalGui;
import mc.apptoeat.com.bot.utils.gui.PlayerGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SettingGui {
  private final SettingManager manager;
  
  private final HashMap<Setting, GlobalGui> guis;
  
  public SettingManager getManager() {
    return this.manager;
  }
  
  public HashMap<Setting, GlobalGui> getGuis() {
    return this.guis;
  }
  
  private final int theme = 15;
  
  private final GlobalGui mainGui;
  
  public int getTheme() {
    getClass();
    return 15;
  }
  
  public GlobalGui getMainGui() {
    return this.mainGui;
  }
  
  private final String message = "&aYou have modified &e%setting% &ato &e%value%&a.";
  
  public String getMessage() {
    getClass();
    return "&aYou have modified &e%setting% &ato &e%value%&a.";
  }
  
  public SettingGui(SettingManager manager, PlayerGui profiles) {
    this.manager = manager;
    this.guis = new HashMap<>();
    this.mainGui = new GlobalGui("&c&lBot Settings", (int)MathUtils.floor((manager.getSettings().size() * 2), 9.0D, false) + 18);
    createSubGuis();
    createMain(profiles);
  }
  
  public void createSubGuis() {
    this.manager.getSettings().forEach(setting -> {
          GlobalGui gui = new GlobalGui(setting.getFancyName(), (int)MathUtils.floor((setting.getSettings().size() * 3 + 2), 27.0D, false));
          gui.setGui(());
          this.guis.put(setting, gui);
        });
  }
  
  public void createMain(PlayerGui profiles) {
    this.mainGui.setGui(() -> {
          this.mainGui.reset();
          int slot = 0;
          for (Setting setting : this.manager.getSettings()) {
            this.mainGui.createGuiItem(new ItemStack(setting.getMaterial(), 1, (short)setting.getId()), setting.getSlot(), setting.getFancyName(), (), setting.getDescription());
            slot += 2;
          } 
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 6 + 9;
          this.mainGui.createGuiItemUsingName(this.manager.getSkullItem("Xecivity", false), slot, "&cChange Bot Name", (), new String[0]);
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 7 + 9;
          this.mainGui.createGuiItemUsingName(this.manager.getSkullItem("Xecivity", true), slot, "&cChange Bot Skin", (), new String[0]);
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 1 + 9;
          this.mainGui.createGuiItem(new ItemStack(Material.WOOL, 1, (short)5), slot, "&a&lSave", (), new String[0]);
          if (profiles != null) {
            slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 2 + 9;
            this.mainGui.createGuiItem(new ItemStack(Material.NAME_TAG, 1), slot, "&e&lLoad Presets", (), new String[0]);
          } 
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 4 + 9;
          this.mainGui.createGuiItem(new ItemStack(Material.WOOL, 1, (short)14), slot, "&c&lReset", (), new String[0]);
          this.mainGui.setBackGroundColor(15);
        });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\gui\imple\SettingGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */