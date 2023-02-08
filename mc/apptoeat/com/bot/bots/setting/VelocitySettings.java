package mc.apptoeat.com.bot.bots.setting;

import org.bukkit.Material;

public class VelocitySettings extends Setting {
  public void setHorizontal(SettingObject horizontal) {
    this.horizontal = horizontal;
  }
  
  public void setVertical(SettingObject vertical) {
    this.vertical = vertical;
  }
  
  public void setDependentRange(SettingObject dependentRange) {
    this.dependentRange = dependentRange;
  }
  
  public void setDependentRangeMultiplier(SettingObject dependentRangeMultiplier) {
    this.dependentRangeMultiplier = dependentRangeMultiplier;
  }
  
  public void setMotionFriction(SettingObject motionFriction) {
    this.motionFriction = motionFriction;
  }
  
  public void setSprintMultiplier(SettingObject sprintMultiplier) {
    this.sprintMultiplier = sprintMultiplier;
  }
  
  public void setGroundMultiplier(SettingObject groundMultiplier) {
    this.groundMultiplier = groundMultiplier;
  }
  
  private SettingObject horizontal = new SettingObject("Horizontal", Material.ANVIL, 0.47D, 0.01D);
  
  public SettingObject getHorizontal() {
    return this.horizontal;
  }
  
  private SettingObject vertical = new SettingObject("Vertical", Material.ANVIL, 0.35D, 0.01D);
  
  public SettingObject getVertical() {
    return this.vertical;
  }
  
  private SettingObject dependentRange = new SettingObject("Dependent Range", Material.BLAZE_ROD, 3.2D, 0.1D);
  
  public SettingObject getDependentRange() {
    return this.dependentRange;
  }
  
  private SettingObject dependentRangeMultiplier = new SettingObject("Dependent Range Multiplier", Material.BLAZE_POWDER, 0.15D, 0.01D);
  
  public SettingObject getDependentRangeMultiplier() {
    return this.dependentRangeMultiplier;
  }
  
  private SettingObject motionFriction = new SettingObject("Motion Friction", Material.GOLD_BLOCK, 1.0D, 0.02D);
  
  public SettingObject getMotionFriction() {
    return this.motionFriction;
  }
  
  private SettingObject sprintMultiplier = new SettingObject("Sprint Multiplier", Material.DIAMOND_HOE, 1.2D, 0.1D);
  
  public SettingObject getSprintMultiplier() {
    return this.sprintMultiplier;
  }
  
  private SettingObject groundMultiplier = new SettingObject("Ground Multiplier", Material.DIAMOND_SPADE, 1.1D, 0.1D);
  
  public SettingObject getGroundMultiplier() {
    return this.groundMultiplier;
  }
  
  public VelocitySettings() {
    super(Material.SLIME_BALL, "&cVelocity Settings", "Velocity", 23, new String[] { "&4&m--*----------*--", "&c&lInfo:", "&f  In this category", "&f  you are able to", "&f  modify the bot's", "&f  velocity settings.", "", "&aClick to select!", "&4&m--*----------*--" });
    addSettings(new SettingObject[] { this.horizontal, this.vertical, this.dependentRange, this.dependentRangeMultiplier, this.motionFriction, this.sprintMultiplier, this.groundMultiplier });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\VelocitySettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */