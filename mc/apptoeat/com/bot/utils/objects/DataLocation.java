package mc.apptoeat.com.bot.utils.objects;

import org.bukkit.Location;
import org.bukkit.World;

public class DataLocation {
  private final double x;
  
  private final double y;
  
  private final double z;
  
  private final long time;
  
  public double getX() {
    return this.x;
  }
  
  public double getY() {
    return this.y;
  }
  
  public double getZ() {
    return this.z;
  }
  
  public long getTime() {
    return this.time;
  }
  
  public DataLocation(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.time = System.currentTimeMillis();
  }
  
  public DataLocation(Location loc) {
    this(loc.getX(), loc.getY(), loc.getZ());
  }
  
  public DataLocation(double x, double y, double z, long time) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.time = time;
  }
  
  public Location toLocation(World world) {
    return new Location(world, this.x, this.y, this.z);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\objects\DataLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */