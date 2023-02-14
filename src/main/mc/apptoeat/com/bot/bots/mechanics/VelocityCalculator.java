package mc.apptoeat.com.bot.bots.mechanics;

import java.util.Random;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.bots.setting.VelocitySettings;
import mc.apptoeat.com.bot.utils.MathUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.util.Vector;

public class VelocityCalculator extends Manager {
  private boolean velocityReplace;
  
  private Vector velocity;
  
  private boolean damage;
  
  private EntityPlayer attacker;
  
  private boolean sprinting;
  
  public void setVelocityReplace(boolean velocityReplace) {
    this.velocityReplace = velocityReplace;
  }
  
  public void setDamage(boolean damage) {
    this.damage = damage;
  }
  
  public void setAttacker(EntityPlayer attacker) {
    this.attacker = attacker;
  }
  
  public void setSprinting(boolean sprinting) {
    this.sprinting = sprinting;
  }
  
  public VelocityCalculator(Bot bot) {
    super(bot);
    this.velocity = null;
  }
  
  public boolean isVelocityReplace() {
    return this.velocityReplace;
  }
  
  public Vector getVelocity() {
    return this.velocity;
  }
  
  public boolean isDamage() {
    return this.damage;
  }
  
  public EntityPlayer getAttacker() {
    return this.attacker;
  }
  
  public boolean isSprinting() {
    return this.sprinting;
  }
  
  public void attack(EntityPlayer attacker) {
    this.damage = true;
    this.attacker = attacker;
    this.velocityReplace = true;
  }
  
  public Vector getReplacedVelocity(Vector motion) {
    if (this.velocity == null)
      return getVelocityBot(this.attacker, this.bot, this.sprinting, motion); 
    Vector clonedVelocity = this.velocity.clone();
    this.velocity = null;
    return clonedVelocity;
  }
  
  public Vector getVelocityBot(EntityPlayer attacker, Bot bot, boolean sprinting, Vector motion) {
    Vector dir = MathUtils.getDirection(attacker.yaw);
    VelocitySettings velocitySettings = this.settingManager.getVelocitySettings();
    double h = velocitySettings.getHorizontal().getDoubleValue();
    double reach = velocitySettings.getDependentRange().getDoubleValue();
    double distMultiplier = velocitySettings.getDependentRangeMultiplier().getDoubleValue();
    double v = velocitySettings.getVertical().getDoubleValue();
    double motionMultiplier = velocitySettings.getMotionFriction().getDoubleValue();
    double extraH = Math.min(Math.max((reach - bot.getMovements().getLocation().distance(attacker.getBukkitEntity().getLocation()) - 0.3D) * distMultiplier, 0.0D), 0.4D);
    if (sprinting)
      h *= velocitySettings.getSprintMultiplier().getDoubleValue(); 
    if (bot.getMovements().isOnGround())
      h *= velocitySettings.getGroundMultiplier().getDoubleValue(); 
    h += extraH;
    h -= velocitySettings.getHorizontal().getDoubleValue();
    Vector velocity = dir.clone().multiply(new Vector(h, 1.0D, h));
    motion.multiply(-motionMultiplier);
    motion.setY(0);
    velocity.setY(v);
    if (bot.getDamageManager().isCombo()) {
      double horizontal = 0.4D;
      double vertical = 0.3D;
      double yD = bot.getMovements().getLocation().getY() - attacker.locY;
      if (yD + vertical > 2.5D) {
        horizontal = 0.25D;
        vertical = 2.5D - yD;
      } 
      velocity = dir.clone().multiply(horizontal).setY(vertical);
    } 
    velocity.add(motion);
    double distanceX = bot.getMovements().getLocation().getX() - attacker.locX;
    Random random = new Random();
    double distanceZ;
    for (distanceZ = bot.getMovements().getLocation().getZ() - attacker.locZ; distanceX * distanceX + distanceZ * distanceZ < 1.0E-4D; distanceZ = (random.nextDouble() - random.nextDouble()) * 0.01D)
      distanceX = (random.nextDouble() - random.nextDouble()) * 0.01D; 
    if (!bot.getDamageManager().isCombo())
      velocity.add(getBasedVelocity(distanceX, distanceZ, velocitySettings.getHorizontal().getDoubleValue())); 
    return velocity;
  }
  
  public Vector getBasedVelocity(double d0, double d1, double horizontal) {
    float magnitude = MathHelper.sqrt(d0 * d0 + d1 * d1);
    double x = d0 / magnitude * horizontal;
    double z = d1 / magnitude * horizontal;
    return new Vector(x, 0.0D, z);
  }
  
  public void setVelocity(Vector velocity) {
    this.velocityReplace = true;
    this.velocity = velocity;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\VelocityCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */