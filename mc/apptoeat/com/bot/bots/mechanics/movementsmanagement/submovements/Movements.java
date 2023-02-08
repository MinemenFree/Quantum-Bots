package mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements;

import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.utils.BlockManager;
import mc.apptoeat.com.bot.utils.RayTrace;
import mc.apptoeat.com.bot.utils.objects.AABB;
import mc.apptoeat.com.bot.utils.objects.CraftiFriction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Movements extends Manager {
  private final double blockingMultiplier = 0.2D;
  
  private final boolean advancedReduce;
  
  private boolean lastOnGround;
  
  public Movements(Bot bot) {
    super(bot);
    this.blockingMultiplier = 0.2D;
    this.advancedReduce = this.bot.getSettingManager().getClickSetting().getAdvancedReduce().isBooleanValue();
  }
  
  public void tickPre() {
    move();
  }
  
  public void move() {
    Vector motion = getDirection(this.bot.getMovements().getLocation().getYaw() + this.bot.getMovements().getStrafe(), 0.0F);
    float friction = this.bot.getMovements().getFriction();
    double movementSpeed = 0.1D;
    int speed = this.bot.getEffectsManager().getLevel(PotionEffectType.SPEED);
    movementSpeed += 0.02D * speed;
    if (this.bot.getMovements().isOnGround()) {
      if (this.bot.getMovements().isSprint())
        movementSpeed *= 1.3D; 
      movementSpeed *= 0.16277135908603668D / Math.pow(friction, 3.0D);
      if (this.bot.getMovements().isJump() && this.bot.getMovements().isOnGround())
        movementSpeed += 0.2D; 
    } else {
      movementSpeed = 0.02D;
      if (this.bot.getMovements().isSprint())
        movementSpeed = 0.026D; 
      this.bot.getMovements().setFriction(0.91F);
      motion.setY((this.bot.getMovements().getMotionY() - 0.08D) * 0.9800000190734863D);
    } 
    this.bot.getMovements().setFriction(friction);
    if (this.bot.getMovements().isUse())
      movementSpeed *= 0.2D; 
    if (!this.bot.getMovements().isMove())
      movementSpeed *= 0.0D; 
    movementSpeed *= this.bot.getMovements().getSpeedMultiplier();
    motion.setX(motion.getX() * movementSpeed);
    motion.setZ(motion.getZ() * movementSpeed);
    boolean velocityTaken = false;
    if (this.bot.getVelocityCalculator().isVelocityReplace()) {
      velocityTaken = true;
      Vector velocityKb = this.bot.getVelocityCalculator().getReplacedVelocity(motion.clone());
      this.bot.getVelocityCalculator().setVelocityReplace(false);
      double attackMultiplier = Math.max(this.bot.getMovements().getAttackMultiplier(), 0.6D);
      if (this.advancedReduce && this.bot.getMovements().isSprint())
        velocityKb.multiply(new Vector(attackMultiplier, 1.0D, attackMultiplier)); 
      motion.setY(0);
      motion.add(velocityKb);
    } 
    if (this.bot.getMovements().isJump() && this.bot.getMovements().isOnGround()) {
      double jumpValue = 0.42D + this.bot.getEffectsManager().getLevel(PotionEffectType.JUMP) * 0.1D;
      motion.setY(jumpValue);
      this.bot.getMovements().setJump(false);
    } 
    sendMove(motion.clone(), !velocityTaken);
    updateValues(motion.clone(), true);
  }
  
  public void sendMove(Vector motion, boolean addLastMotion) {
    this.bot.getMovements().move(motion, addLastMotion);
  }
  
  public void updateValues(Vector motion, boolean updateFriction) {
    Location loc = this.bot.getMovements().getLocation().toVector().subtract(new Vector(0.0D, 0.01D, 0.0D)).toLocation(this.bot.getMovements().getLocation().getWorld());
    Material block = BlockManager.getBlockType(loc);
    this.bot.getMovements().setOnGround(onGround(loc));
    if (updateFriction) {
      boolean onGround = this.bot.getMovements().isOnGround();
      boolean lastOnGround = this.lastOnGround;
      this.lastOnGround = onGround;
      float friction = CraftiFriction.getFactor(block) * 0.91F;
      if (!lastOnGround)
        friction = 0.91F; 
      this.bot.getMovements().setFriction(friction);
    } 
  }
  
  public boolean onGround(Location loc) {
    AABB playerBox = this.bot.getMovements().getCollision().getPlayerBox();
    World world = loc.getWorld();
    Location min = loc.toVector().add(playerBox.getMin()).toLocation(world);
    Location max = loc.toVector().add(playerBox.getMax().clone().setY(0)).toLocation(world);
    double var1 = loc.getY() - loc.getBlockY();
    double var2 = var1 % 0.0625D;
    double var3 = var1 % 0.0625D - 0.0625D;
    double var4 = Math.abs(Math.min(var2, var3));
    boolean ground = RayTrace.blockCollidesCheck(min, max);
    return ground;
  }
  
  public Vector getDirection(float yaw, float pitch) {
    Vector result = new Vector(0, 0, 0);
    double xz = Math.cos(Math.toRadians(pitch));
    result.setX(-xz * Math.sin(Math.toRadians(yaw)));
    result.setZ(xz * Math.cos(Math.toRadians(yaw)));
    return result;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\movementsmanagement\submovements\Movements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */