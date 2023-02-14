package mc.apptoeat.com.bot.utils.objects;

import org.bukkit.util.Vector;

public class AABB implements Cloneable {
  private Vector min;
  
  private Vector max;
  
  public AABB(Vector min, Vector max) {
    this.min = min;
    this.max = max;
  }
  
  public void translate(Vector vector) {
    this.min.add(vector);
    this.max.add(vector);
  }
  
  public void translateTo(Vector vector) {
    this.max.setX(vector.getX() + this.max.getX() - this.min.getX());
    this.max.setY(vector.getY() + this.max.getY() - this.min.getY());
    this.max.setZ(vector.getZ() + this.max.getZ() - this.min.getZ());
    this.min.setX(vector.getX());
    this.min.setY(vector.getY());
    this.min.setZ(vector.getZ());
  }
  
  public boolean isColliding(AABB other) {
    if (this.max.getX() < other.getMin().getX() || this.min.getX() > other.getMax().getX())
      return false; 
    if (this.max.getY() < other.getMin().getY() || this.min.getY() > other.getMax().getY())
      return false; 
    return (this.max.getZ() >= other.getMin().getZ() && this.min.getZ() <= other.getMax().getZ());
  }
  
  public boolean isColliding(Vector loc) {
    double minX = this.min.getX();
    double minY = this.min.getY();
    double minZ = this.min.getZ();
    double maxX = this.max.getX();
    double maxY = this.max.getY();
    double maxZ = this.max.getZ();
    double x = loc.getX();
    double y = loc.getY();
    double z = loc.getZ();
    return (minX < x && x < maxX && minY < y && y < maxY && minZ < z && z < maxZ);
  }
  
  public AABB clone() {
    try {
      AABB clone = (AABB)super.clone();
      clone.min = this.min.clone();
      clone.max = this.max.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public Vector getMax() {
    return this.max;
  }
  
  public Vector getMin() {
    return this.min;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\objects\AABB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */