package mc.apptoeat.com.bot.bots.mechanics.movementsmanagement;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.FinalMoveEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements.Collision;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements.Movements;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements.Rotations;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements.TargetLocate;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.RayTrace;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MovementsMain extends Manager {
  private Location location;
  
  public void setLocation(Location location) {
    this.location = location;
  }
  
  public void setStrafe(float strafe) {
    this.strafe = strafe;
  }
  
  public void setLastMotion(Vector lastMotion) {
    this.lastMotion = lastMotion;
  }
  
  public void setTargetLocate(TargetLocate targetLocate) {
    this.targetLocate = targetLocate;
  }
  
  public void setSentLocationUpdate(boolean sentLocationUpdate) {
    this.sentLocationUpdate = sentLocationUpdate;
  }
  
  public void setUse(boolean use) {
    this.use = use;
  }
  
  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }
  
  public void setAttackSprint(boolean attackSprint) {
    this.attackSprint = attackSprint;
  }
  
  public void setReset(boolean reset) {
    this.reset = reset;
  }
  
  public void setSneak(boolean sneak) {
    this.sneak = sneak;
  }
  
  public void setSprint(boolean sprint) {
    this.sprint = sprint;
  }
  
  public void setFriction(float friction) {
    this.friction = friction;
  }
  
  public void setMotionY(double motionY) {
    this.motionY = motionY;
  }
  
  public void setMove(boolean move) {
    this.move = move;
  }
  
  public void setEdge(boolean edge) {
    this.edge = edge;
  }
  
  public void setMovements(Movements movements) {
    this.movements = movements;
  }
  
  public void setCollision(Collision collision) {
    this.collision = collision;
  }
  
  public void setRotations(Rotations rotations) {
    this.rotations = rotations;
  }
  
  public void setJump(boolean jump) {
    this.jump = jump;
  }
  
  public void setAttackMultiplier(double attackMultiplier) {
    this.attackMultiplier = attackMultiplier;
  }
  
  public void setSpeedMultiplier(double speedMultiplier) {
    this.speedMultiplier = speedMultiplier;
  }
  
  public void setEdgeDetector(boolean edgeDetector) {
    this.edgeDetector = edgeDetector;
  }
  
  public void setGroundTicks(int groundTicks) {
    this.groundTicks = groundTicks;
  }
  
  public void setDelayedBotLocation(boolean delayedBotLocation) {
    this.delayedBotLocation = delayedBotLocation;
  }
  
  public Location getLocation() {
    return this.location;
  }
  
  private float strafe = 0.0F;
  
  public float getStrafe() {
    return this.strafe;
  }
  
  private Vector lastMotion = new Vector(0, 0, 0);
  
  private TargetLocate targetLocate;
  
  private boolean sentLocationUpdate;
  
  private boolean use;
  
  public Vector getLastMotion() {
    return this.lastMotion;
  }
  
  public TargetLocate getTargetLocate() {
    return this.targetLocate;
  }
  
  public boolean isSentLocationUpdate() {
    return this.sentLocationUpdate;
  }
  
  public boolean isUse() {
    return this.use;
  }
  
  private boolean onGround = true;
  
  public boolean isOnGround() {
    return this.onGround;
  }
  
  private boolean attackSprint = true;
  
  public boolean isAttackSprint() {
    return this.attackSprint;
  }
  
  private boolean reset = false;
  
  public boolean isReset() {
    return this.reset;
  }
  
  private boolean sneak = false;
  
  public boolean isSneak() {
    return this.sneak;
  }
  
  private boolean sprint = true;
  
  public boolean isSprint() {
    return this.sprint;
  }
  
  private float friction = 0.91F;
  
  private double motionY;
  
  public float getFriction() {
    return this.friction;
  }
  
  public double getMotionY() {
    return this.motionY;
  }
  
  private boolean move = true;
  
  private boolean edge;
  
  private Movements movements;
  
  private Collision collision;
  
  private Rotations rotations;
  
  private boolean jump;
  
  public boolean isMove() {
    return this.move;
  }
  
  public boolean isEdge() {
    return this.edge;
  }
  
  public Movements getMovements() {
    return this.movements;
  }
  
  public Collision getCollision() {
    return this.collision;
  }
  
  public Rotations getRotations() {
    return this.rotations;
  }
  
  public boolean isJump() {
    return this.jump;
  }
  
  private double attackMultiplier = 1.0D;
  
  private double speedMultiplier;
  
  private boolean edgeDetector;
  
  private int groundTicks;
  
  private boolean delayedBotLocation;
  
  public double getAttackMultiplier() {
    return this.attackMultiplier;
  }
  
  public double getSpeedMultiplier() {
    return this.speedMultiplier;
  }
  
  public boolean isEdgeDetector() {
    return this.edgeDetector;
  }
  
  public int getGroundTicks() {
    return this.groundTicks;
  }
  
  public MovementsMain(Bot bot, Location loc, Player target) {
    super(bot);
    this.delayedBotLocation = this.bot.getSettingManager().getConnectionSettings().getDelayedBotLocation().isBooleanValue();
    this.location = loc;
    bot.getMEventManager().addEvent((Manager)(this.targetLocate = new TargetLocate(bot, target)));
    bot.getMEventManager().addEvent((Manager)(this.collision = new Collision(bot)));
    bot.getMEventManager().addEvent((Manager)(this.movements = new Movements(bot)));
    bot.getMEventManager().addEvent((Manager)(this.rotations = new Rotations(bot)));
  }
  
  public boolean isDelayedBotLocation() {
    return this.delayedBotLocation;
  }
  
  public void tickMid() {
    if (this.sentLocationUpdate)
      if (this.delayedBotLocation) {
        (new BukkitRunnable() {
            final Location location = MovementsMain.this.getBot().getMovements().getLocation();
            
            public void run() {
              MovementsMain.this.getBot().getPacketsManager().updateLocation(this.location);
            }
          }).runTaskLater((Plugin)main.getInstance(), (this.settingManager.getConnectionSettings().getPing().getIntValue() / 100));
      } else {
        getBot().getPacketsManager().updateLocation(getBot().getMovements().getLocation());
      }  
    this.sentLocationUpdate = false;
    this.attackMultiplier = 1.0D;
    this.speedMultiplier = 1.0D;
  }
  
  public void move(Vector motion, boolean addLastMotion) {
    Location oldLoc = this.location.clone();
    boolean collidesCheck = !RayTrace.blockCollidesCheck(oldLoc, this.collision.getPlayerBox());
    Vector loc = this.location.toVector();
    this.lastMotion.multiply(new Vector(this.bot.getMovements().getAttackMultiplier(), 1.0D, this.bot.getMovements().getAttackMultiplier()));
    if (addLastMotion)
      motion.add(this.lastMotion.clone().setY(0).multiply(this.friction)); 
    loc.add(motion.clone());
    Location newLoc = loc.toLocation(this.location.getWorld());
    Location updatedLoc = newLoc.clone();
    updatedLoc = this.collision.cast(oldLoc.clone(), newLoc.clone());
    updatedLoc.setYaw(this.location.getYaw());
    updatedLoc.setPitch(this.location.getPitch());
    callEvent((Event)new FinalMoveEvent(this.bot, updatedLoc, this.location));
    this.location = updatedLoc;
    Vector offSet = this.location.toVector().clone().subtract(oldLoc.toVector().clone());
    motion = offSet;
    this.motionY = offSet.getY();
    this.lastMotion = motion.clone();
    updatedLoc();
    if (this.use || !this.sprint || !this.move)
      this.reset = true; 
    if (this.reset && (!this.use || this.sprint || this.move)) {
      this.attackSprint = true;
      this.reset = false;
    } 
  }
  
  public void attack() {
    if (this.sprint)
      this.attackMultiplier *= 0.6D; 
  }
  
  public void updatedLoc() {
    this.sentLocationUpdate = true;
  }
  
  public void rotate(float yaw, float pitch) {
    this.location.setYaw(yaw);
    this.location.setPitch(pitch);
    updatedLoc();
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\movementsmanagement\MovementsMain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */