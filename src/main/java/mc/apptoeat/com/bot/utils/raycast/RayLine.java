package mc.apptoeat.com.bot.utils.raycast;

public class RayLine {
  private double min;
  
  private double max;
  
  private double lineSize;
  
  public void setMin(double min) {
    this.min = min;
  }
  
  public void setMax(double max) {
    this.max = max;
  }
  
  public void setLineSize(double lineSize) {
    this.lineSize = lineSize;
  }
  
  public double getMin() {
    return this.min;
  }
  
  public double getMax() {
    return this.max;
  }
  
  public double getLineSize() {
    return this.lineSize;
  }
  
  public RayLine(double min, double max, double lineSize) {
    this.min = min;
    this.max = max;
    this.lineSize = lineSize;
  }
  
  public double ratioMin() {
    return this.min / this.lineSize;
  }
  
  public double ratioMax() {
    return this.max / this.lineSize;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\raycast\RayLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */