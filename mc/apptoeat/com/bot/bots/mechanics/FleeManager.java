package mc.apptoeat.com.bot.bots.mechanics;

import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.RunningEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements.TargetLocate;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class FleeManager extends Manager {
  private boolean active;
  
  public void setActive(boolean active) {
    this.active = active;
  }
  
  public void setClcBlocks(double clcBlocks) {
    this.clcBlocks = clcBlocks;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  public void setTicks(int ticks) {
    this.ticks = ticks;
  }
  
  public void setOffTicks(int offTicks) {
    this.offTicks = offTicks;
  }
  
  public void setRunning(boolean running) {
    this.running = running;
  }
  
  public void setRunningFromCombo(boolean runningFromCombo) {
    this.runningFromCombo = runningFromCombo;
  }
  
  public boolean isActive() {
    return this.active;
  }
  
  private double clcBlocks = this.settingManager.getFleeSettings().getCalculatedDistance().getDoubleValue();
  
  public double getClcBlocks() {
    return this.clcBlocks;
  }
  
  private boolean enabled = true;
  
  private int ticks;
  
  private int offTicks;
  
  private boolean running;
  
  private boolean runningFromCombo;
  
  public boolean isEnabled() {
    return this.enabled;
  }
  
  public int getTicks() {
    return this.ticks;
  }
  
  public int getOffTicks() {
    return this.offTicks;
  }
  
  public boolean isRunning() {
    return this.running;
  }
  
  public boolean isRunningFromCombo() {
    return this.runningFromCombo;
  }
  
  public FleeManager(Bot bot) {
    super(bot);
  }
  
  public void tickPre() {
    TargetLocate finder = this.bot.getMovements().getTargetLocate();
    this.active = check();
    if (this.active) {
      this.ticks++;
      this.offTicks = 0;
    } 
    if (!this.active) {
      this.ticks = 0;
      this.offTicks++;
    } 
    if (this.running) {
      RunningEvent runningEvent;
      callEvent((Event)(runningEvent = new RunningEvent(this.bot, this.ticks)));
      if (runningEvent.isForceEnd()) {
        this.running = false;
        this.active = false;
      } 
    } 
    finder.setOverwriteLocation(!this.active);
    if (!this.active)
      return; 
    Vector direction = this.bot.getMovements().getLocation().toVector().subtract(finder.getTargetEntity().getLocation().toVector()).setY(0).normalize();
    Location oppositeLoc = direction.multiply(this.clcBlocks).add(this.bot.getMovements().getLocation().toVector()).toLocation(this.bot.getMovements().getLocation().getWorld());
    finder.setTarget(oppositeLoc);
  }
  
  public boolean check() {
    if (!this.enabled)
      return false; 
    return (this.bot.getInventoryManager().isRequiredToEat() || this.running || this.bot.getInventoryManager().isEating() || this.runningFromCombo);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\FleeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */