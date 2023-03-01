package mc.apptoeat.com.bot.utils.raycast;

import mc.apptoeat.com.bot.utils.objects.AABB;
import org.bukkit.util.Vector;

public class RayCollision {
  private final Vector from;
  
  private final Vector to;
  
  private final AABB collision;
  
  public Vector getFrom() {
    return this.from;
  }
  
  public Vector getTo() {
    return this.to;
  }
  
  public AABB getCollision() {
    return this.collision;
  }
  
  public RayCollision(Vector from, Vector to, AABB collision) {
    this.from = from;
    this.to = to;
    this.collision = collision;
  }
  
  public Vector collides() {
    Vector collMin = this.collision.getMin();
    Vector collMax = this.collision.getMax();
    RayLine lineX = getRayLine(this.from.getX(), this.to.getX(), collMin.getX(), Double.valueOf(collMax.getX()));
    RayLine lineY = getRayLine(this.from.getY(), this.to.getY(), collMin.getY(), Double.valueOf(collMax.getY()));
    RayLine lineZ = getRayLine(this.from.getZ(), this.to.getZ(), collMin.getZ(), Double.valueOf(collMax.getZ()));
    if (lineX == null || lineY == null || lineZ == null)
      return null; 
    double ratioMinMeetingPoint = max(lineX.ratioMin(), lineY.ratioMin(), lineZ.ratioMin());
    double ratioMaxMeetingPoint = min(lineX.ratioMax(), lineY.ratioMax(), lineZ.ratioMax());
    double meetingPointX = ratioMinMeetingPoint * lineX.getLineSize();
    double meetingPointY = ratioMinMeetingPoint * lineY.getLineSize();
    double meetingPointZ = ratioMinMeetingPoint * lineZ.getLineSize();
    return new Vector(meetingPointX, meetingPointY, meetingPointZ);
  }
  
  public RayLine getRayLine(double rayVar1, double rayVar2, double collMin, Double collMax) {
    double rayMin = Math.min(rayVar1, rayVar2);
    double rayMax = Math.max(rayVar1, rayVar2);
    double collidesMin = Math.max(rayMin, collMin);
    double collidesMax = Math.min(rayMax, collMax.doubleValue());
    if (collidesMin > collidesMax)
      return null; 
    if (rayMin != rayVar1) {
      double clonedMin = collidesMin;
      collidesMin = collidesMax;
      collidesMax = clonedMin;
    } 
    return new RayLine(collidesMin, collidesMax, rayMax - rayMin);
  }
  
  public double max(double var1, double var2, double var3) {
    double var4 = Math.max(var1, var2);
    return Math.max(var3, var4);
  }
  
  public double min(double var1, double var2, double var3) {
    double var4 = Math.min(var1, var2);
    return Math.min(var3, var4);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\raycast\RayCollision.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */