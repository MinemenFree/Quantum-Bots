package mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.submovements;

import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TargetLocate extends Manager {
  private Location target;
  
  private final Player targetEntity;
  
  private boolean overwriteLocation;
  
  public void setTarget(Location target) {
    this.target = target;
  }
  
  public void setOverwriteLocation(boolean overwriteLocation) {
    this.overwriteLocation = overwriteLocation;
  }
  
  public void setDelayedPlayerLocation(boolean delayedPlayerLocation) {
    this.delayedPlayerLocation = delayedPlayerLocation;
  }
  
  public Location getTarget() {
    return this.target;
  }
  
  public Player getTargetEntity() {
    return this.targetEntity;
  }
  
  public boolean isOverwriteLocation() {
    return this.overwriteLocation;
  }
  
  private boolean delayedPlayerLocation = true;
  
  public boolean isDelayedPlayerLocation() {
    return this.delayedPlayerLocation;
  }
  
  public TargetLocate(Bot bot, Player target) {
    super(bot);
    this.targetEntity = target;
    this.target = this.targetEntity.getLocation();
    this.overwriteLocation = true;
  }
  
  public void tickPre() {
    if (this.overwriteLocation)
      this.target = this.bot.getLagCompensatorManager().getClientLocation(); 
    if (!this.delayedPlayerLocation && this.overwriteLocation)
      this.target = this.targetEntity.getLocation(); 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\movementsmanagement\submovements\TargetLocate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */