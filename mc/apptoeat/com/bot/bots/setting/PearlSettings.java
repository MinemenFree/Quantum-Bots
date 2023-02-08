package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class PearlSettings extends Setting {
  private SettingObject pearlDelay = new SettingObject("Pearl Delay", Material.ENDER_PEARL, 600, 5.0D);
  
  public SettingObject getPearlDelay() {
    return this.pearlDelay;
  }
  
  private SettingObject angleDifference = new SettingObject("Angle Difference", Material.NETHER_STAR, 130, 5.0D);
  
  public SettingObject getAngleDifference() {
    return this.angleDifference;
  }
  
  private SettingObject pearlThrowYawOffSet = new SettingObject("Pearl Throw YawOffSet", Material.NAME_TAG, 10, 1.0D);
  
  public SettingObject getPearlThrowYawOffSet() {
    return this.pearlThrowYawOffSet;
  }
  
  private SettingObject pearlingTicks = new SettingObject("Pearling Ticks", Material.WATCH, 10, 1.0D);
  
  public SettingObject getPearlingTicks() {
    return this.pearlingTicks;
  }
  
  public PearlSettings() {
    super(Material.ENDER_PEARL, "&cPearl Settings", "Pearl", 12, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  pearl settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.angleDifference, this.pearlThrowYawOffSet, this.pearlDelay, this.pearlingTicks });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\PearlSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */