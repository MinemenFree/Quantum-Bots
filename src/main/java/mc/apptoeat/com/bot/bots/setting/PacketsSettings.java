package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class PacketsSettings extends Setting {
  private final SettingObject relativeMove = new SettingObject("Relative Move", Material.STRING, false);
  
  public SettingObject getRelativeMove() {
    return this.relativeMove;
  }
  
  public PacketsSettings() {
    super(Material.STRING, "&cPackets Settings", "Packets", 0, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  packets settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSetting(this.relativeMove);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\PacketsSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */