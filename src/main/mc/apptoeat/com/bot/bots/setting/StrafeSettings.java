package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class StrafeSettings extends Setting {
  public void setEnabled(SettingObject enabled) {
    this.enabled = enabled;
  }
  
  public void setChance(SettingObject chance) {
    this.chance = chance;
  }
  
  public void setStrafeInCombo(SettingObject strafeInCombo) {
    this.strafeInCombo = strafeInCombo;
  }
  
  public void setSwitchSideTicks(SettingObject switchSideTicks) {
    this.switchSideTicks = switchSideTicks;
  }
  
  private SettingObject enabled = new SettingObject("Strafe", Material.SUGAR_CANE, false);
  
  public SettingObject getEnabled() {
    return this.enabled;
  }
  
  private SettingObject chance = new SettingObject("Chance", Material.GOLDEN_CARROT, 100);
  
  public SettingObject getChance() {
    return this.chance;
  }
  
  private SettingObject strafeInCombo = new SettingObject("Strafe In Combo", Material.SUGAR, false);
  
  public SettingObject getStrafeInCombo() {
    return this.strafeInCombo;
  }
  
  private SettingObject switchSideTicks = new SettingObject("Switch Side Ticks", Material.TRAP_DOOR, 20, 1.0D);
  
  public SettingObject getSwitchSideTicks() {
    return this.switchSideTicks;
  }
  
  public StrafeSettings() {
    super(Material.SUGAR_CANE, "&cStrafe Settings", "Strafe", 11, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  strafe settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.enabled, this.chance, this.strafeInCombo, this.switchSideTicks });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\StrafeSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */