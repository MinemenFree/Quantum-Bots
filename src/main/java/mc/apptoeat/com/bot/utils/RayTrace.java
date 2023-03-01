package mc.apptoeat.com.bot.utils;

import mc.apptoeat.com.bot.utils.objects.AABB;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class RayTrace {
  private final Location from;
  
  public RayTrace(Location from) {
    this.from = from;
  }
  
  public Vector traceWithBlocks(AABB playerBox, Location to, double accurate) {
    Vector dir = to.toVector().subtract(this.from.toVector()).normalize();
    Vector from = this.from.toVector();
    double reach = to.toVector().distance(from);
    World world = to.getWorld();
    Vector lastValidPos = from;
    double i;
    for (i = 0.0D; i < reach; i += accurate) {
      Vector newPos = from.clone().add(dir.clone().multiply(i));
      Location min = newPos.clone().add(playerBox.getMin()).toLocation(world);
      Location max = newPos.clone().add(playerBox.getMax()).toLocation(world);
      if (blockCollidesCheck(min, max))
        return lastValidPos; 
      lastValidPos = newPos;
    } 
    return to.toVector();
  }
  
  public static boolean blockCollidesCheck(Location loc, AABB playerBox) {
    Location min = loc.clone().add(playerBox.getMin());
    Location max = loc.clone().add(playerBox.getMax());
    return blockCollidesCheck(min, max);
  }
  
  public static boolean blockCollidesCheck(Location min, Location max) {
    for (Block block : MathUtils.blocksFromTwoPoints(min, max)) {
      AABB bHitBox = BlockManager.blockHitBox(block);
      if (bHitBox.isColliding(new AABB(min.toVector(), max.toVector())) && !block.getType().equals(Material.AIR) && !block.isLiquid() && block.getType().isSolid())
        return true; 
    } 
    return false;
  }
  
  public static class TraceResults {
    private final Location collidesLoc;
    
    private final boolean collides;
    
    public Location getCollidesLoc() {
      return this.collidesLoc;
    }
    
    public boolean isCollides() {
      return this.collides;
    }
    
    public TraceResults(Location collidesLoc, boolean collides) {
      this.collidesLoc = collidesLoc;
      this.collides = collides;
    }
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\RayTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */