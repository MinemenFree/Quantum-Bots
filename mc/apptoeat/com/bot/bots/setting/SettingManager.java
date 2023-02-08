package mc.apptoeat.com.bot.bots.setting;

import java.util.ArrayList;
import java.util.List;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.FileManager;
import mc.apptoeat.com.bot.utils.config.ConfigManager;
import mc.apptoeat.com.bot.utils.sql.SQLInfo;
import mc.apptoeat.com.bot.utils.sql.SettingSQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SettingManager {
  private AttackSettings clickSetting;
  
  private ArrayList<Setting> settings;
  
  private RotationSetting rotationSetting;
  
  private ConnectionSettings connectionSettings;
  
  private FleeSettings fleeSettings;
  
  private VelocitySettings velocitySettings;
  
  private PotionSetting potionSetting;
  
  private EatingSettings eatingSettings;
  
  private StrafeSettings strafeSettings;
  
  private PearlSettings pearlSettings;
  
  private PacketsSettings packetsSettings;
  
  private WTapSetting wTapSetting;
  
  public void setClickSetting(AttackSettings clickSetting) {
    this.clickSetting = clickSetting;
  }
  
  public void setSettings(ArrayList<Setting> settings) {
    this.settings = settings;
  }
  
  public void setRotationSetting(RotationSetting rotationSetting) {
    this.rotationSetting = rotationSetting;
  }
  
  public void setConnectionSettings(ConnectionSettings connectionSettings) {
    this.connectionSettings = connectionSettings;
  }
  
  public void setFleeSettings(FleeSettings fleeSettings) {
    this.fleeSettings = fleeSettings;
  }
  
  public void setVelocitySettings(VelocitySettings velocitySettings) {
    this.velocitySettings = velocitySettings;
  }
  
  public void setPotionSetting(PotionSetting potionSetting) {
    this.potionSetting = potionSetting;
  }
  
  public void setEatingSettings(EatingSettings eatingSettings) {
    this.eatingSettings = eatingSettings;
  }
  
  public void setStrafeSettings(StrafeSettings strafeSettings) {
    this.strafeSettings = strafeSettings;
  }
  
  public void setPearlSettings(PearlSettings pearlSettings) {
    this.pearlSettings = pearlSettings;
  }
  
  public void setPacketsSettings(PacketsSettings packetsSettings) {
    this.packetsSettings = packetsSettings;
  }
  
  public void setWTapSetting(WTapSetting wTapSetting) {
    this.wTapSetting = wTapSetting;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setFoundPath(String foundPath) {
    this.foundPath = foundPath;
  }
  
  public void setDescription(List<String> description) {
    this.description = description;
  }
  
  public AttackSettings getClickSetting() {
    return this.clickSetting;
  }
  
  public ArrayList<Setting> getSettings() {
    return this.settings;
  }
  
  public RotationSetting getRotationSetting() {
    return this.rotationSetting;
  }
  
  public ConnectionSettings getConnectionSettings() {
    return this.connectionSettings;
  }
  
  public FleeSettings getFleeSettings() {
    return this.fleeSettings;
  }
  
  public VelocitySettings getVelocitySettings() {
    return this.velocitySettings;
  }
  
  public PotionSetting getPotionSetting() {
    return this.potionSetting;
  }
  
  public EatingSettings getEatingSettings() {
    return this.eatingSettings;
  }
  
  public StrafeSettings getStrafeSettings() {
    return this.strafeSettings;
  }
  
  public PearlSettings getPearlSettings() {
    return this.pearlSettings;
  }
  
  public PacketsSettings getPacketsSettings() {
    return this.packetsSettings;
  }
  
  public WTapSetting getWTapSetting() {
    return this.wTapSetting;
  }
  
  private String name = "default";
  
  private String foundPath;
  
  public String getName() {
    return this.name;
  }
  
  public String getFoundPath() {
    return this.foundPath;
  }
  
  private String botName = "%player%";
  
  public String getBotName() {
    return this.botName;
  }
  
  private String skin = "%player%";
  
  public String getSkin() {
    return this.skin;
  }
  
  private List<String> description = new ArrayList<>();
  
  public List<String> getDescription() {
    return this.description;
  }
  
  public SettingManager() {
    this.settings = new ArrayList<>();
    this.settings.add(this.clickSetting = new AttackSettings());
    this.settings.add(this.velocitySettings = new VelocitySettings());
    this.settings.add(this.rotationSetting = new RotationSetting());
    this.settings.add(this.potionSetting = new PotionSetting());
    this.settings.add(this.fleeSettings = new FleeSettings());
    this.settings.add(this.eatingSettings = new EatingSettings());
    this.settings.add(this.strafeSettings = new StrafeSettings());
    this.settings.add(this.pearlSettings = new PearlSettings());
    this.settings.add(this.connectionSettings = new ConnectionSettings());
    this.packetsSettings = new PacketsSettings();
    this.settings.add(this.wTapSetting = new WTapSetting());
  }
  
  public ItemStack getSkullItem(String player, boolean skin) {
    ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    SkullMeta meta = (SkullMeta)stack.getItemMeta();
    if (skin)
      meta.setOwner(this.skin.replace("%player%", player)); 
    if (!skin)
      meta.setOwner(this.botName.replace("%player%", player)); 
    stack.setItemMeta((ItemMeta)meta);
    return stack.clone();
  }
  
  public Setting getSettingByName(String name) {
    for (Setting setting : this.settings) {
      if (setting.getFancyName().equals(name))
        return setting; 
    } 
    return null;
  }
  
  public Setting getSettingByNameRemoveColor(String name) {
    for (Setting setting : this.settings) {
      if (ChatColor.stripColor(name).equals(name))
        return setting; 
    } 
    return null;
  }
  
  public void resetSettings() {
    (new BukkitRunnable() {
        public void run() {
          SettingManager defaults = new SettingManager();
          SettingManager.this.settings.forEach(setting -> setting.getSettings().forEach(()));
        }
      }).runTaskAsynchronously((Plugin)main.getInstance());
  }
  
  public void update(final SettingManager updated) {
    (new BukkitRunnable() {
        public void run() {
          SettingManager.this.settings.forEach(setting -> setting.getSettings().forEach(()));
        }
      }).runTaskAsynchronously((Plugin)main.getInstance());
  }
  
  public void saveSettings(String path, JavaPlugin plugin) {
    if (!main.getInstance().getSqlManager().isEnabled()) {
      this.settings.forEach(setting -> setting.getSettings().forEach(()));
    } else {
      SettingSQL mongoSetting = new SettingSQL(path);
      this.settings.forEach(setting -> setting.getSettings().forEach(()));
    } 
  }
  
  public static SettingManager loadSettings(String path, JavaPlugin main, String loadPath, String name) {
    SettingManager manager = loadSettings(path, main, loadPath);
    manager.setName(name);
    return manager;
  }
  
  public static SettingManager loadSettings(String path, JavaPlugin javaPlugin, String loadPath) {
    String botName, botSkin;
    SettingManager settingManager = new SettingManager();
    if (!main.getInstance().getSqlManager().isEnabled()) {
      botName = (String)FileManager.getOrDefault(ConfigManager.data, path + ".BotName", "%player%");
      botSkin = (String)FileManager.getOrDefault(ConfigManager.data, path + ".BotSkin", "%player%");
    } else {
      botName = sqlInfo().getOrDefaultNames(path, "BotName", "%player%");
      botSkin = sqlInfo().getOrDefaultNames(path, "BotSkin", "%player%");
      Bukkit.broadcastMessage("botName " + botName + " path " + path);
      System.out.println("DEBUG: botName " + botName + " path " + path);
    } 
    settingManager.setBotNameWithoutUpdate(Color.code("&c" + botName));
    settingManager.setSkinWithoutUpdate(botSkin);
    settingManager.setFoundPath(path);
    if (!main.getInstance().getSqlManager().isEnabled()) {
      settingManager.getSettings().forEach(setting -> setting.getSettings().forEach(()));
    } else {
      SettingSQL mongoSetting = new SettingSQL(path);
      SettingSQL mongoSettingUpdate = new SettingSQL(loadPath);
      settingManager.getSettings().forEach(setting -> setting.getSettings().forEach(()));
      mongoSetting.update();
    } 
    return settingManager;
  }
  
  private static SQLInfo sqlInfo() {
    return main.getInstance().getSqlInfo();
  }
  
  public void setSkin(String skin) {
    this.skin = skin;
    if (!main.getInstance().getSqlManager().isEnabled()) {
      FileManager.update(ConfigManager.data, this.foundPath + ".BotSkin", skin);
    } else {
      sqlInfo().updateDataNames(this.foundPath, "BotSkin", skin);
    } 
  }
  
  public void setBotName(String botName) {
    this.botName = botName;
    if (!main.getInstance().getSqlManager().isEnabled()) {
      FileManager.update(ConfigManager.data, this.foundPath + ".BotName", botName);
    } else {
      sqlInfo().updateDataNames(this.foundPath, "BotName", botName);
    } 
  }
  
  public void setSkinWithoutUpdate(String skin) {
    this.skin = skin;
  }
  
  public void setBotNameWithoutUpdate(String botName) {
    this.botName = botName;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\SettingManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */