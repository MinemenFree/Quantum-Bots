package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class EatingSettings extends Setting {
  public void setStartEatingTimer(SettingObject startEatingTimer) {
    this.startEatingTimer = startEatingTimer;
  }
  
  public void setStartEatingDistance(SettingObject startEatingDistance) {
    this.startEatingDistance = startEatingDistance;
  }
  
  public void setEatingGappleHealth(SettingObject eatingGappleHealth) {
    this.eatingGappleHealth = eatingGappleHealth;
  }
  
  public void setCancelEatingDistance(SettingObject cancelEatingDistance) {
    this.cancelEatingDistance = cancelEatingDistance;
  }
  
  public void setPreGap(SettingObject preGap) {
    this.preGap = preGap;
  }
  
  private SettingObject startEatingTimer = new SettingObject("Start Eating Timer", Material.GOLDEN_APPLE, 40, 2.0D);
  
  public SettingObject getStartEatingTimer() {
    return this.startEatingTimer;
  }
  
  private SettingObject startEatingDistance = new SettingObject("Start Eating Distance", Material.MUSHROOM_SOUP, 7.0D, 1.0D);
  
  public SettingObject getStartEatingDistance() {
    return this.startEatingDistance;
  }
  
  private SettingObject eatingGappleHealth = new SettingObject("Eating Gapple Health", Material.GOLDEN_APPLE, 15, 1.0D);
  
  public SettingObject getEatingGappleHealth() {
    return this.eatingGappleHealth;
  }
  
  private SettingObject cancelEatingDistance = new SettingObject("Jump Eating Distance", Material.BARRIER, 4.5D, 0.1D);
  
  public SettingObject getCancelEatingDistance() {
    return this.cancelEatingDistance;
  }
  
  private SettingObject preGap = new SettingObject("preGap", Material.GOLDEN_CARROT, true);
  
  public SettingObject getPreGap() {
    return this.preGap;
  }
  
  public EatingSettings() {
    super(Material.GOLDEN_APPLE, "&cGapple Settings", "Gapple", 14, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  gapple settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.preGap, this.startEatingDistance, this.startEatingTimer, this.eatingGappleHealth, this.cancelEatingDistance });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\EatingSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */