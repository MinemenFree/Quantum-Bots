package mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.RotationEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.MovementsMain;
import mc.apptoeat.com.bot.utils.MathUtils;
import mc.apptoeat.com.bot.utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Rotations extends Manager {
  private float speed;
  
  private double JitterRotation;
  
  private double rotationSpeedRandom;
  
  private Location targetPos;
  
  private float lastYaw;
  
  public void setSpeed(float speed) {
    this.speed = speed;
  }
  
  public void setJitterRotation(double JitterRotation) {
    this.JitterRotation = JitterRotation;
  }
  
  public void setRotationSpeedRandom(double rotationSpeedRandom) {
    this.rotationSpeedRandom = rotationSpeedRandom;
  }
  
  public void setTargetPos(Location targetPos) {
    this.targetPos = targetPos;
  }
  
  public void setLastYaw(float lastYaw) {
    this.lastYaw = lastYaw;
  }
  
  public Rotations(Bot bot) {
    super(bot);
    this.speed = this.bot.getSettingManager().getRotationSetting().getSpeed().getIntValue();
    this.JitterRotation = this.bot.getSettingManager().getRotationSetting().getJitterRotation().getDoubleValue();
    this.rotationSpeedRandom = this.bot.getSettingManager().getRotationSetting().getSpeedRandom().getDoubleValue();
    this.targetPos = null;
  }
  
  public float getSpeed() {
    return this.speed;
  }
  
  public double getJitterRotation() {
    return this.JitterRotation;
  }
  
  public double getRotationSpeedRandom() {
    return this.rotationSpeedRandom;
  }
  
  public Location getTargetPos() {
    return this.targetPos;
  }
  
  public float getLastYaw() {
    return this.lastYaw;
  }
  
  public void tickPre() {
    float yaw = this.bot.getMovements().getLocation().getYaw();
    float pitch = this.bot.getMovements().getLocation().getPitch();
    MovementsMain movements = this.bot.getMovements();
    Location tPos = movements.getTargetLocate().getTarget();
    Vector aimBot = WorldUtils.calculateDirectionDifference(movements.getLocation(), tPos);
    this.lastYaw = yaw;
    yaw = aimAssist(yaw, (float)aimBot.getX()) + (float)MathUtils.getRandom(-this.JitterRotation, this.JitterRotation);
    pitch = aimAssistPitch(pitch, (float)aimBot.getZ()) + (float)MathUtils.getRandom(-this.JitterRotation, this.JitterRotation);
    if (Math.abs(pitch) > 90.0F)
      pitch = 90.0F * pitch / Math.abs(pitch); 
    callEvent((Event)new RotationEvent(this.bot, yaw, pitch, this.bot.getMovements().getLocation().getYaw(), this.bot.getMovements().getLocation().getPitch()));
    movements.rotate(yaw, pitch);
  }
  
  public float aimAssistPitch(float before, float after) {
    double delta = (after - before);
    double speed = (this.speed + (float)MathUtils.getRandom(-this.rotationSpeedRandom, this.rotationSpeedRandom));
    if (Math.abs(delta) <= speed)
      return after; 
    return (float)(before + delta / Math.abs(delta) * speed);
  }
  
  public float aimAssist(float before, float after) {
    double speed = (this.speed + (float)MathUtils.getRandom(-this.rotationSpeedRandom, this.rotationSpeedRandom));
    double original = (after - before);
    float var1 = after + 360.0F - before;
    float var2 = after - before + 360.0F;
    float var3 = after + 360.0F - before + 360.0F;
    double deltaYaw = Math.min(min(Math.abs(var1), Math.abs(var2), Math.abs(var3)), Math.abs(original));
    if (deltaYaw == Math.abs(var1))
      deltaYaw = var1; 
    if (deltaYaw == Math.abs(var2))
      deltaYaw = var2; 
    if (deltaYaw == Math.abs(var3))
      deltaYaw = var3; 
    if (deltaYaw == Math.abs(original))
      deltaYaw = original; 
    if (Math.abs(deltaYaw) > speed)
      return (float)(before + deltaYaw / Math.abs(deltaYaw) * speed); 
    return after;
  }
  
  public double min(double var1, double var2, double var3) {
    double var4 = Math.min(var1, var2);
    return Math.min(var3, var4);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\movementsmanagement\submovements\Rotations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */