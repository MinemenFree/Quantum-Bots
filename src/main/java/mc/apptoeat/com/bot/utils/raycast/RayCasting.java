package mc.apptoeat.com.bot.utils.raycast;

import java.util.Arrays;
import java.util.List;
import mc.apptoeat.com.bot.utils.objects.AABB;
import org.bukkit.util.Vector;

public class RayCasting {
  public static Vector hitboxIntersection(Vector current, Vector next, AABB hitbox) {
    Vector origin = new Vector(current.getX(), current.getY(), current.getZ());
    Vector nextPoint = new Vector(next.getX(), next.getY(), next.getZ());
    Vector direction = nextPoint.clone().subtract(origin.clone()).normalize();
    double minX = hitbox.getMin().getX();
    double minY = hitbox.getMin().getY();
    double minZ = hitbox.getMin().getZ();
    double maxX = hitbox.getMax().getX();
    double maxY = hitbox.getMax().getY();
    double maxZ = hitbox.getMax().getZ();
    Vector p2 = new Vector(minX, maxY, maxZ);
    Vector p3 = new Vector(maxX, maxY, minZ);
    Vector p4 = new Vector(minX, maxY, minZ);
    Vector p5 = new Vector(maxX, minY, maxZ);
    Vector p6 = new Vector(minX, minY, maxZ);
    Vector p7 = new Vector(maxX, minY, minZ);
    List<Plane> planes = Arrays.asList(new Plane[] { new Plane(p2, p3, p4), new Plane(p2, p5, p6), new Plane(p3, p5, p7), new Plane(p4, p3, p7), new Plane(p2, p4, p6), new Plane(p5, p6, p7) });
    Vector closestIntersection = null;
    double closestDistance = Double.MAX_VALUE;
    for (Plane plane : planes) {
      Vector intersection = linePlaneIntersectionPoint(plane.getPoint1(), plane.getPoint2(), plane.getPoint3(), origin, direction);
      if (intersection != null) {
        boolean isWithinXBounds = isBetween(intersection.getX(), minX, maxX);
        boolean isWithinYBounds = isBetween(intersection.getY(), minY, maxY);
        boolean isWithinZBounds = isBetween(intersection.getZ(), minZ, maxZ);
        double distance = intersection.distance(origin);
        if (isWithinXBounds && isWithinYBounds && isWithinZBounds && distance < closestDistance) {
          closestIntersection = intersection;
          closestDistance = distance;
        } 
      } 
    } 
    return closestIntersection;
  }
  
  public static Vector linePlaneIntersectionPoint(Vector planePoint1, Vector planePoint2, Vector planePoint3, Vector origin, Vector direction) {
    Vector normalVector = normalVectorForPlane(planePoint1, planePoint2, planePoint3);
    double num = normalVector.getX() * (origin.getX() - planePoint1.getX()) + normalVector.getY() * (origin.getY() - planePoint1.getY()) + normalVector.getZ() * (origin.getZ() - planePoint1.getZ());
    double den = normalVector.getX() * direction.getX() + normalVector.getY() * direction.getY() + normalVector.getZ() * direction.getZ();
    if (den == 0.0D)
      return null; 
    double t = -(num / den);
    return new Vector(origin
        .getX() + direction.getX() * t, origin
        .getY() + direction.getY() * t, origin
        .getZ() + direction.getZ() * t);
  }
  
  public static Vector normalVectorForPlane(Vector planePoint1, Vector planePoint2, Vector planePoint3) {
    Vector p1 = planePoint1.clone();
    Vector p2 = planePoint2.clone();
    Vector p3 = planePoint3.clone();
    return p1.subtract(p2).crossProduct(p2.subtract(p3)).normalize();
  }
  
  public static class Plane {
    private final Vector point1;
    
    private final Vector point2;
    
    private final Vector point3;
    
    public Plane(Vector point1, Vector point2, Vector point3) {
      this.point1 = point1;
      this.point2 = point2;
      this.point3 = point3;
    }
    
    public Vector getPoint1() {
      return this.point1;
    }
    
    public Vector getPoint2() {
      return this.point2;
    }
    
    public Vector getPoint3() {
      return this.point3;
    }
  }
  
  public static boolean isBetween(double var1, double min, double max) {
    return (var1 >= min && var1 <= max);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\raycast\RayCasting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */