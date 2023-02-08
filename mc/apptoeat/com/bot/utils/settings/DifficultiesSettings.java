package mc.apptoeat.com.bot.utils.settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.Command;
import mc.apptoeat.com.bot.utils.gui.GlobalGui;
import mc.apptoeat.com.bot.utils.gui.imple.SettingGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DifficultiesSettings {
  private final Map<String, SettingManager> data;
  
  private Map<String, SettingGui> guiMap;
  
  private GlobalGui mainGui;
  
  public Map<String, SettingManager> getData() {
    return this.data;
  }
  
  public Map<String, SettingGui> getGuiMap() {
    return this.guiMap;
  }
  
  public GlobalGui getMainGui() {
    return this.mainGui;
  }
  
  public DifficultiesSettings() {
    this.data = new HashMap<>();
    this.guiMap = new HashMap<>();
    loadSetting("EASY", "&aEasy");
    loadSetting("NORMAL", "&eNormal");
    loadSetting("HARD", "&cHard");
    loadSetting("EXPERT", "&4Expert");
    loadGui();
    loadCommand();
  }
  
  public SettingManager getData(String setting) {
    if (this.data.containsKey(setting))
      return this.data.get(setting); 
    return null;
  }
  
  public void loadSetting(String setting, String fancyName) {
    SettingManager manager = SettingManager.loadSettings("botSetting." + setting, (JavaPlugin)main.getInstance(), "botSetting." + setting);
    this.data.put(setting, manager);
    this.guiMap.put(fancyName, new SettingGui(manager, null));
  }
  
  public void loadGui() {
    this.mainGui = new GlobalGui("&cBot Difficulty Manager", 9);
    this.mainGui.setGui(() -> {
          this.mainGui.reset();
          int[] slot = { 0 };
          this.guiMap.forEach(());
        });
  }
  
  public void loadCommand() {
    Command command = new Command("difficulties", "Practice 1v1 Difficulty Manager", "/difficulties", p -> {
        
        });
    command.setOnCommand(player -> {
          if (!player.hasPermission("bot.staff")) {
            player.sendMessage(Color.code("&cYou do not have permission to execute this command."));
            return;
          } 
          this.mainGui.minOpen(player);
        });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\settings\DifficultiesSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */