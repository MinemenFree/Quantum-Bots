package mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.utils.BlockManager;
import mc.apptoeat.com.bot.utils.MathUtils;
import mc.apptoeat.com.bot.utils.RayTrace;
import mc.apptoeat.com.bot.utils.objects.AABB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class Collision extends Manager {
  private final double raytraceAccuracy = 0.005D;
  
  private Location lastValidLoc;
  
  private final double xzHitBoxOffset;
  
  private final AABB playerBox;
  
  private boolean inWater;
  
  private List<Location> pastLocations;
  
  private Runnable waterEnterEvent;
  
  private int stuckTicks;
  
  public void setLastValidLoc(Location lastValidLoc) {
    this.lastValidLoc = lastValidLoc;
  }
  
  public void setInWater(boolean inWater) {
    this.inWater = inWater;
  }
  
  public void setPastLocations(List<Location> pastLocations) {
    this.pastLocations = pastLocations;
  }
  
  public void setWaterEnterEvent(Runnable waterEnterEvent) {
    this.waterEnterEvent = waterEnterEvent;
  }
  
  public void setStuckTicks(int stuckTicks) {
    this.stuckTicks = stuckTicks;
  }
  
  public Collision(Bot bot) {
    super(bot);
    this.raytraceAccuracy = 0.005D;
    this.xzHitBoxOffset = this.bot.getSettingManager().getClickSetting().getPlayerHitBox().getDoubleValue();
    this.playerBox = new AABB(new Vector(-this.xzHitBoxOffset, 0.0D, -this.xzHitBoxOffset), new Vector(this.xzHitBoxOffset, 1.8D, this.xzHitBoxOffset));
    this.inWater = false;
    this.pastLocations = new ArrayList<>();
  }
  
  public double getRaytraceAccuracy() {
    getClass();
    return 0.005D;
  }
  
  public Location getLastValidLoc() {
    return this.lastValidLoc;
  }
  
  public double getXzHitBoxOffset() {
    return this.xzHitBoxOffset;
  }
  
  public AABB getPlayerBox() {
    return this.playerBox;
  }
  
  public boolean isInWater() {
    return this.inWater;
  }
  
  public List<Location> getPastLocations() {
    return this.pastLocations;
  }
  
  public Runnable getWaterEnterEvent() {
    return this.waterEnterEvent;
  }
  
  public int getStuckTicks() {
    return this.stuckTicks;
  }
  
  public Location cast(Location from, Location to) {
    if (Math.abs(from.distance(to)) > 100.0D)
      to = from.clone(); 
    RayTrace trace = new RayTrace(from.clone());
    Location yTo = to.clone();
    Location yToClone = yTo.clone();
    Location xzTo = to.clone();
    yTo.setX(from.getX());
    yTo.setZ(from.getZ());
    xzTo.setY(from.getY());
    Location xzToClone = xzTo.clone();
    Location result1 = trace.traceWithBlocks(this.playerBox, yTo, 0.005D).toLocation(to.getWorld());
    Location result2 = trace.traceWithBlocks(this.playerBox, xzTo, 0.005D).toLocation(to.getWorld());
    if ((result2.getX() != xzToClone.getX() || result2.getZ() != xzToClone.getZ()) && result1.getY() == yToClone.getY() && this.bot.getMovements().isOnGround() && !collidesCheckSolid(xzToClone.clone().add(0.0D, 1.05D, 0.0D)))
      this.bot.getMovements().setJump(true); 
    result1.setX(result2.getX());
    result1.setZ(result2.getZ());
    Location result3 = trace.traceWithBlocks(this.playerBox, result1, 0.005D).toLocation(to.getWorld());
    this.inWater = collidesCheck(result3, new Material[] { Material.WATER, Material.STATIONARY_WATER, Material.WATER_LILY });
    if (this.waterEnterEvent != null && this.inWater)
      this.waterEnterEvent.run(); 
    if (result3.distance(from) < 0.02D && this.bot.getMovements().isMove()) {
      this.stuckTicks++;
    } else {
      this.stuckTicks = 0;
    } 
    if (this.stuckTicks == 0) {
      this.pastLocations.add(result3);
      if (this.pastLocations.size() > 5)
        this.pastLocations.remove(0); 
    } 
    if (this.stuckTicks > 3) {
      Bukkit.broadcastMessage("setback");
      return this.pastLocations.get(0);
    } 
    return result3;
  }
  
  public AABB getHitBox(Location location) {
    Vector min = location.clone().toVector().add(new Vector(-0.3D, 0.0D, -0.3D));
    Vector max = location.clone().toVector().add(new Vector(0.3D, 1.8D, 0.3D));
    return new AABB(min, max);
  }
  
  public boolean collidesCheck(Location location, Material... type) {
    World world = location.getWorld();
    Location min = location.clone().toVector().add(new Vector(-0.3D, 0.0D, -0.3D)).toLocation(world);
    Location max = location.clone().toVector().add(new Vector(0.3D, 1.0D, 0.3D)).toLocation(world);
    return collides(min, max, type);
  }
  
  public boolean collides(Location min, Location max, Material... type) {
    for (Block block : MathUtils.blocksFromTwoPoints(min, max)) {
      AABB bHitBox = BlockManager.blockHitBox(block);
      if (bHitBox.isColliding(new AABB(min.toVector(), max.toVector())) && Arrays.<Material>asList(type).contains(block.getType()))
        return true; 
    } 
    return false;
  }
  
  public boolean collidesCheckSolid(Location location) {
    World world = location.getWorld();
    Location min = location.clone().toVector().add(new Vector(-0.3D, 0.0D, -0.3D)).toLocation(world);
    Location max = location.clone().toVector().add(new Vector(0.3D, 1.0D, 0.3D)).toLocation(world);
    return collidesSolid(min, max);
  }
  
  public boolean collidesSolid(Location min, Location max) {
    for (Block block : MathUtils.blocksFromTwoPoints(min, max)) {
      AABB bHitBox = BlockManager.blockHitBox(block);
      if (bHitBox.isColliding(new AABB(min.toVector(), max.toVector())) && block.getType().isSolid())
        return true; 
    } 
    return false;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\movementsmanagement\submovements\Collision.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */