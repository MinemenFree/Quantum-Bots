package mc.apptoeat.com.bot.bots.mechanics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.objects.DataLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LagCompensatorManager extends Manager {
  private List<DataLocation> locations;
  
  public void setLocations(List<DataLocation> locations) {
    this.locations = locations;
  }
  
  public void setMaxLocations(int maxLocations) {
    this.maxLocations = maxLocations;
  }
  
  public void setLastPlayer(UUID lastPlayer) {
    this.lastPlayer = lastPlayer;
  }
  
  public List<DataLocation> getLocations() {
    return this.locations;
  }
  
  private int maxLocations = 20;
  
  private UUID lastPlayer;
  
  public int getMaxLocations() {
    return this.maxLocations;
  }
  
  public UUID getLastPlayer() {
    return this.lastPlayer;
  }
  
  public Player getPlayer() {
    if (this.bot.getMovements() == null)
      return null; 
    if (this.bot.getMovements().getTargetLocate() == null)
      return null; 
    return this.bot.getMovements().getTargetLocate().getTargetEntity();
  }
  
  public LagCompensatorManager(final Bot bot) {
    super(bot);
    this.locations = new ArrayList<>();
    (new BukkitRunnable() {
        public void run() {
          if (!bot.isAlive()) {
            cancel();
            return;
          } 
          Player p = LagCompensatorManager.this.getPlayer();
          if (p == null)
            return; 
          if (LagCompensatorManager.this.lastPlayer == null)
            LagCompensatorManager.this.lastPlayer = p.getUniqueId(); 
          if (LagCompensatorManager.this.lastPlayer != p.getUniqueId())
            LagCompensatorManager.this.locations.clear(); 
          LagCompensatorManager.this.lastPlayer = p.getUniqueId();
          LagCompensatorManager.this.movePacket(p.getLocation());
        }
      }).runTaskTimer((Plugin)main.getInstance(), 1L, 1L);
  }
  
  public void movePacket(Location loc) {
    if (loc.getX() == 0.0D && loc.getY() == 0.0D && loc.getZ() == 0.0D)
      return; 
    this.locations.add(new DataLocation(loc));
    if (this.locations.size() > this.maxLocations)
      this.locations.remove(this.locations.get(0)); 
  }
  
  public DataLocation getPrevLocation(long time) {
    DataLocation orElse = new DataLocation(getPlayer().getLocation());
    if (this.locations.size() > 0)
      orElse = this.locations.get(this.locations.size() - 1); 
    return this.locations.stream().min(Comparator.comparingLong(loc -> Math.abs(loc.getTime() - System.currentTimeMillis() - time))).orElse(orElse);
  }
  
  public Location getClientLocation() {
    return getPrevLocation((this.settingManager.getConnectionSettings().getPing().getIntValue() + this.settingManager.getConnectionSettings().getConnectionPacketDelay().getIntValue())).toLocation(this.bot.getMovements().getLocation().getWorld());
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\LagCompensatorManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */