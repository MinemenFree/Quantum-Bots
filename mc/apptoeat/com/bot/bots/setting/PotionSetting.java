package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class PotionSetting extends Setting {
  public void setFleeHealth(SettingObject fleeHealth) {
    this.fleeHealth = fleeHealth;
  }
  
  public void setAgroPotion(SettingObject agroPotion) {
    this.agroPotion = agroPotion;
  }
  
  public void setFirstPotionTick(SettingObject firstPotionTick) {
    this.firstPotionTick = firstPotionTick;
  }
  
  public void setSecondPotionTick(SettingObject secondPotionTick) {
    this.secondPotionTick = secondPotionTick;
  }
  
  public void setFinishPotionTick(SettingObject finishPotionTick) {
    this.finishPotionTick = finishPotionTick;
  }
  
  public void setTickOffset(SettingObject tickOffset) {
    this.tickOffset = tickOffset;
  }
  
  public void setSecondPotion(SettingObject secondPotion) {
    this.secondPotion = secondPotion;
  }
  
  public void setCancelPotionDistance(SettingObject cancelPotionDistance) {
    this.cancelPotionDistance = cancelPotionDistance;
  }
  
  private SettingObject fleeHealth = new SettingObject("Flee Health", Material.APPLE, 4, 1.0D);
  
  public SettingObject getFleeHealth() {
    return this.fleeHealth;
  }
  
  private SettingObject agroPotion = new SettingObject("Agro Potion", Material.DIAMOND_SWORD, false);
  
  public SettingObject getAgroPotion() {
    return this.agroPotion;
  }
  
  private SettingObject firstPotionTick = new SettingObject("First Potion Tick", Material.WATCH, 7, 1.0D);
  
  public SettingObject getFirstPotionTick() {
    return this.firstPotionTick;
  }
  
  private SettingObject secondPotionTick = new SettingObject("Second Potion Tick", Material.WATCH, 11, 1.0D);
  
  public SettingObject getSecondPotionTick() {
    return this.secondPotionTick;
  }
  
  private SettingObject finishPotionTick = new SettingObject("Finish Potion Tick", Material.WATCH, 20, 1.0D);
  
  public SettingObject getFinishPotionTick() {
    return this.finishPotionTick;
  }
  
  private SettingObject tickOffset = new SettingObject("Tick Offset", Material.DIODE, 1, 1.0D);
  
  public SettingObject getTickOffset() {
    return this.tickOffset;
  }
  
  private SettingObject secondPotion = new SettingObject("Second Potion", Material.POTION, true);
  
  public SettingObject getSecondPotion() {
    return this.secondPotion;
  }
  
  private SettingObject cancelPotionDistance = new SettingObject("Cancel Potion Distance", Material.HOPPER, 3.0D, 0.1D);
  
  public SettingObject getCancelPotionDistance() {
    return this.cancelPotionDistance;
  }
  
  public PotionSetting() {
    super(Material.POTION, "&cPotion Settings", "Potion", 15, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  potion settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    setId(16428);
    addSettings(new SettingObject[] { this.agroPotion, this.fleeHealth, this.firstPotionTick, this.secondPotionTick, this.finishPotionTick, this.tickOffset, this.cancelPotionDistance, this.secondPotion });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\PotionSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */