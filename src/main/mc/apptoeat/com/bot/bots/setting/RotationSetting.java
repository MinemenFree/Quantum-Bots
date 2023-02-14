package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class RotationSetting extends Setting {
  public void setSpeed(SettingObject speed) {
    this.speed = speed;
  }
  
  public void setJitterRotation(SettingObject jitterRotation) {
    this.jitterRotation = jitterRotation;
  }
  
  public void setSpeedRandom(SettingObject speedRandom) {
    this.speedRandom = speedRandom;
  }
  
  private SettingObject speed = new SettingObject("Speed", Material.EXP_BOTTLE, 18);
  
  public SettingObject getSpeed() {
    return this.speed;
  }
  
  private SettingObject jitterRotation = new SettingObject("Jitter Rotation", Material.FLINT, 1.0D);
  
  public SettingObject getJitterRotation() {
    return this.jitterRotation;
  }
  
  private SettingObject speedRandom = new SettingObject("Speed Random", Material.FIREWORK, 1.0D);
  
  public SettingObject getSpeedRandom() {
    return this.speedRandom;
  }
  
  public RotationSetting() {
    super(Material.WEB, "&cRotation Settings", "Rotation", 13, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  rotation settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.speed, this.jitterRotation, this.speedRandom });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\RotationSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */