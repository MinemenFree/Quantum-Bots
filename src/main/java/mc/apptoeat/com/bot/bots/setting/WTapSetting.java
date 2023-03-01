package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class WTapSetting extends Setting {
  private SettingObject enabled = new SettingObject("W-Tap", Material.DIAMOND_HELMET, true);
  
  public SettingObject getEnabled() {
    return this.enabled;
  }
  
  private SettingObject chance = new SettingObject("Chance", Material.DIAMOND_CHESTPLATE, 100, 25.0D);
  
  public SettingObject getChance() {
    return this.chance;
  }
  
  private SettingObject tapLong = new SettingObject("Tap Long", Material.DIAMOND_LEGGINGS, 1, 1.0D);
  
  public SettingObject getTapLong() {
    return this.tapLong;
  }
  
  private SettingObject comboTapLong = new SettingObject("Combo Tap Long", Material.DIAMOND_AXE, 1, 1.0D);
  
  public SettingObject getComboTapLong() {
    return this.comboTapLong;
  }
  
  private SettingObject tapStart = new SettingObject("Tap Start", Material.DIAMOND_BOOTS, 3, 1.0D);
  
  public SettingObject getTapStart() {
    return this.tapStart;
  }
  
  private SettingObject blockingHitting = new SettingObject("BlockHitting", Material.DIAMOND_SWORD, false);
  
  public SettingObject getBlockingHitting() {
    return this.blockingHitting;
  }
  
  public WTapSetting() {
    super(Material.DIAMOND_BOOTS, "&cW-Tap Settings", "WTap", 21, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  w-tap settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.enabled, this.chance, this.comboTapLong, this.tapLong, this.tapStart, this.blockingHitting });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\WTapSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */