package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class FleeSettings extends Setting {
  public void setCalculatedDistance(SettingObject calculatedDistance) {
    this.calculatedDistance = calculatedDistance;
  }
  
  public void setRunFromCombos(SettingObject runFromCombos) {
    this.runFromCombos = runFromCombos;
  }
  
  public void setRunFromCombosHits(SettingObject runFromCombosHits) {
    this.runFromCombosHits = runFromCombosHits;
  }
  
  public void setRunFromCombosTime(SettingObject runFromCombosTime) {
    this.runFromCombosTime = runFromCombosTime;
  }
  
  private SettingObject calculatedDistance = new SettingObject("Calculated Distance", Material.WATCH, 10.0D);
  
  public SettingObject getCalculatedDistance() {
    return this.calculatedDistance;
  }
  
  private SettingObject runFromCombos = new SettingObject("Run From Combos", Material.DIAMOND_BOOTS, false);
  
  public SettingObject getRunFromCombos() {
    return this.runFromCombos;
  }
  
  private SettingObject runFromCombosHits = new SettingObject("Run From Combo Hits", Material.DIAMOND_SWORD, 3, 1.0D);
  
  public SettingObject getRunFromCombosHits() {
    return this.runFromCombosHits;
  }
  
  private SettingObject runFromCombosTime = new SettingObject("Run From Combo Ticks", Material.WATCH, 20, 1.0D);
  
  public SettingObject getRunFromCombosTime() {
    return this.runFromCombosTime;
  }
  
  public FleeSettings() {
    super(Material.FEATHER, "&cFlee Settings", "Flee", 22, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  flee settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.calculatedDistance, this.runFromCombos, this.runFromCombosHits, this.runFromCombosTime });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\FleeSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */