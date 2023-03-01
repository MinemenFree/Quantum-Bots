package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class ConnectionSettings extends Setting {
  public void setPing(SettingObject ping) {
    this.ping = ping;
  }
  
  public void setDelayedBotLocation(SettingObject delayedBotLocation) {
    this.delayedBotLocation = delayedBotLocation;
  }
  
  public void setConnectionPacketDelay(SettingObject connectionPacketDelay) {
    this.connectionPacketDelay = connectionPacketDelay;
  }
  
  private SettingObject ping = new SettingObject("Ping", Material.STRING, 100, 5.0D);
  
  public SettingObject getPing() {
    return this.ping;
  }
  
  private SettingObject delayedBotLocation = new SettingObject("Delayed Bot Location", Material.WATCH, true);
  
  public SettingObject getDelayedBotLocation() {
    return this.delayedBotLocation;
  }
  
  private SettingObject connectionPacketDelay = new SettingObject("Rel-Move Simulation Ticks", Material.SPIDER_EYE, 3, 1.0D);
  
  public SettingObject getConnectionPacketDelay() {
    return this.connectionPacketDelay;
  }
  
  public ConnectionSettings() {
    super(Material.DIODE, "&cConnection Settings", "Connection", 16, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  connection settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.ping, this.delayedBotLocation, this.connectionPacketDelay });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\ConnectionSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */