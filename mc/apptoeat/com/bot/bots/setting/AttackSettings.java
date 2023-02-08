package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class AttackSettings extends Setting {
  public void setReach(SettingObject reach) {
    this.reach = reach;
  }
  
  public void setCps(SettingObject cps) {
    this.cps = cps;
  }
  
  public void setSwingRange(SettingObject swingRange) {
    this.swingRange = swingRange;
  }
  
  public void setPlayerHitBox(SettingObject playerHitBox) {
    this.playerHitBox = playerHitBox;
  }
  
  public void setAdvancedReduce(SettingObject advancedReduce) {
    this.advancedReduce = advancedReduce;
  }
  
  private SettingObject reach = new SettingObject("Reach", Material.DIAMOND_SWORD, 2.7D, 0.1D);
  
  public SettingObject getReach() {
    return this.reach;
  }
  
  private SettingObject cps = new SettingObject("CPS", Material.DIAMOND_CHESTPLATE, 6);
  
  public SettingObject getCps() {
    return this.cps;
  }
  
  private SettingObject swingRange = new SettingObject("Swing Range", Material.IRON_SWORD, 6);
  
  public SettingObject getSwingRange() {
    return this.swingRange;
  }
  
  private SettingObject playerHitBox = new SettingObject("Player Hitbox", Material.IRON_CHESTPLATE, 0.3D, 0.1D);
  
  public SettingObject getPlayerHitBox() {
    return this.playerHitBox;
  }
  
  private SettingObject advancedReduce = new SettingObject("Reduce", Material.STICK, false);
  
  public SettingObject getAdvancedReduce() {
    return this.advancedReduce;
  }
  
  public int getDelay() {
    return 1000 / this.cps.getIntValue();
  }
  
  public AttackSettings() {
    super(Material.DIAMOND_SWORD, "&cAttack Settings", "Attack", 10, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  attack settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.reach, this.cps, this.swingRange, this.advancedReduce, this.playerHitBox });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\AttackSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */