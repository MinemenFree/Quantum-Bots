package mc.apptoeat.com.bot.utils.gui.imple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import mc.apptoeat.com.bot.bots.setting.Setting;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.bots.setting.SettingObject;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.FileManager;
import mc.apptoeat.com.bot.utils.MathUtils;
import mc.apptoeat.com.bot.utils.gui.PlayerGui;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class BotGui {
  private PlayerGui mainGui;
  
  private DataPlayer data;
  
  private SettingManager manager;
  
  private HashMap<Setting, PlayerGui> guis;
  
  private PlayerGui profiles;
  
  public void setMainGui(PlayerGui mainGui) {
    this.mainGui = mainGui;
  }
  
  public void setData(DataPlayer data) {
    this.data = data;
  }
  
  public void setManager(SettingManager manager) {
    this.manager = manager;
  }
  
  public void setGuis(HashMap<Setting, PlayerGui> guis) {
    this.guis = guis;
  }
  
  public void setProfiles(PlayerGui profiles) {
    this.profiles = profiles;
  }
  
  public void setTheme(int theme) {
    this.theme = theme;
  }
  
  public void setMaxProfiles(int maxProfiles) {
    this.maxProfiles = maxProfiles;
  }
  
  public void setDefaultPath(String defaultPath) {
    this.defaultPath = defaultPath;
  }
  
  public void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }
  
  public PlayerGui getMainGui() {
    return this.mainGui;
  }
  
  public DataPlayer getData() {
    return this.data;
  }
  
  public SettingManager getManager() {
    return this.manager;
  }
  
  public HashMap<Setting, PlayerGui> getGuis() {
    return this.guis;
  }
  
  private int theme = 15;
  
  public int getTheme() {
    return this.theme;
  }
  
  private final String message = "&aYou have modified &e%setting% &ato &e%value%&a.";
  
  private int maxProfiles;
  
  public String getMessage() {
    getClass();
    return "&aYou have modified &e%setting% &ato &e%value%&a.";
  }
  
  public int getMaxProfiles() {
    return this.maxProfiles;
  }
  
  private String defaultPath = "data.%uuid%";
  
  private boolean loaded;
  
  public String getDefaultPath() {
    return this.defaultPath;
  }
  
  public boolean isLoaded() {
    return this.loaded;
  }
  
  public BotGui(final DataPlayer dataPlayer) {
    (new BukkitRunnable() {
        public void run() {
          String path = BotGui.this.defaultPath.replace("%uuid%", dataPlayer.getPlayer().getUniqueId().toString());
          BotGui.this.manager = SettingManager.loadSettings(path, (JavaPlugin)main.getInstance(), path);
          BotGui.this.mainGui = new PlayerGui("&c&lBot Settings", (int)MathUtils.floor((BotGui.this.manager.getSettings().size() * 2), 9.0D, false) + 18, dataPlayer);
          BotGui.this.data = dataPlayer;
          BotGui.this.maxProfiles = 9;
          BotGui.this.profiles = new PlayerGui("&c&lBot Profiles", BotGui.this.maxProfiles, dataPlayer);
          BotGui.this.guis = (HashMap)new HashMap<>();
          BotGui.this.createSubGuis();
          BotGui.this.createProfiles();
          BotGui.this.createMain();
          BotGui.this.loaded = true;
        }
      }).runTaskAsynchronously((Plugin)main.getInstance());
  }
  
  public void loadSettings(String path) {
    String var1 = path.replace("%uuid%", this.data.getPlayer().getUniqueId().toString());
    String var2 = this.defaultPath.replace("%uuid%", this.data.getPlayer().getUniqueId().toString());
    this.manager = SettingManager.loadSettings(var1, (JavaPlugin)main.getInstance(), var2);
  }
  
  public SettingManager getSettings(String path) {
    String var1 = path.replace("%uuid%", this.data.getPlayer().getUniqueId().toString());
    String var2 = this.defaultPath.replace("%uuid%", this.data.getPlayer().getUniqueId().toString());
    return SettingManager.loadSettings(var1, (JavaPlugin)main.getInstance(), var2);
  }
  
  public void saveSettings() {
    (new BukkitRunnable() {
        public void run() {
          BotGui.this.data.setSaving(true);
          UUID uuid = BotGui.this.data.getPlayer().getUniqueId();
          String path = "settings." + uuid + ".";
          double number = FileManager.getOrDefault((JavaPlugin)main.getInstance(), "settingsID." + uuid, Double.valueOf(1.0D));
          number++;
          main.getInstance().getConfig().set("settingsID." + uuid, Double.valueOf(number));
          main.getInstance().saveConfig();
          path = path + Math.round(number);
          BotGui.this.saveSettings(path);
          (new BukkitRunnable() {
              public void run() {
                BotGui.this.data.setSaving(false);
              }
            }).runTaskLater((Plugin)main.getInstance(), 5L);
        }
      }).runTaskAsynchronously((Plugin)main.getInstance());
  }
  
  public void saveSettings(String path) {
    this.manager.saveSettings(path, (JavaPlugin)main.getInstance());
  }
  
  public void resetSettings() {}
  
  public void createSubGuis() {
    this.manager.getSettings().forEach(setting -> {
          PlayerGui gui = new PlayerGui(setting.getFancyName(), (int)MathUtils.floor((setting.getSettings().size() + 2), 9.0D, false) + 18, this.data);
          gui.setGui(());
          this.guis.put(setting, gui);
        });
  }
  
  public void createMain() {
    this.mainGui.setGui(() -> {
          this.mainGui.reset();
          int slot = 0;
          for (Setting setting : this.manager.getSettings()) {
            this.mainGui.createGuiItem(new ItemStack(setting.getMaterial(), 1, (short)setting.getId()), setting.getSlot(), setting.getFancyName(), (), setting.getDescription());
            slot += 2;
          } 
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 6 + 9;
          this.mainGui.createGuiItemUsingName(this.manager.getSkullItem(this.data.getPlayer().getName(), false), slot, "&cChange Bot Name", (), new String[0]);
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 7 + 9;
          this.mainGui.createGuiItemUsingName(this.manager.getSkullItem(this.data.getPlayer().getName(), true), slot, "&cChange Bot Skin", (), new String[0]);
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 1 + 9;
          this.mainGui.createGuiItem(new ItemStack(Material.WOOL, 1, (short)5), slot, "&a&lSave", (), new String[0]);
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 2 + 9;
          this.mainGui.createGuiItem(new ItemStack(Material.NAME_TAG, 1), slot, "&e&lLoad Presets", (), new String[0]);
          slot = (int)MathUtils.floor((this.manager.getSettings().size() * 2), 9.0D, false) + 4 + 9;
          this.mainGui.createGuiItem(new ItemStack(Material.WOOL, 1, (short)14), slot, "&c&lReset", (), new String[0]);
          this.mainGui.setBackGroundColor(this.theme);
        });
  }
  
  public void createProfiles() {
    this.profiles.setGui(() -> {
          this.profiles.reset();
          List<SettingManager> managers = getProfiles();
          while (managers.size() > this.maxProfiles)
            managers.remove(0); 
          int slot = 0;
          for (SettingManager settingManager : managers) {
            this.profiles.createGuiItem(Material.NAME_TAG, slot, settingManager.getName(), "&c", (), this.manager.getDescription());
            slot++;
          } 
          this.profiles.setBackGroundColor(15);
        });
  }
  
  public List<SettingManager> getProfiles() {
    UUID uuid = this.data.getPlayer().getUniqueId();
    String path = "settings." + uuid;
    List<SettingManager> results = new ArrayList<>();
    ConfigurationSection configurationSection = main.getInstance().getConfig().getConfigurationSection(path);
    if (configurationSection == null)
      return new ArrayList<>(); 
    configurationSection.getKeys(false).forEach(name -> {
          SettingManager manager = SettingManager.loadSettings(path + "." + name, (JavaPlugin)main.getInstance(), this.defaultPath.replace("%uuid%", this.data.getPlayer().getUniqueId().toString()));
          manager.setName(name);
          results.add(manager);
        });
    return results;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\gui\imple\BotGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */