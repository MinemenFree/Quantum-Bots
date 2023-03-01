package mc.apptoeat.com.bot.features.imple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.PearlSettings;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Pearling extends Feature {
  private final Map<Bot, Integer> pearlTick = new HashMap<>();
  
  public void tickEvent(Bot bot) {
    this.pearlTick.put(bot, Integer.valueOf(((Integer)this.pearlTick.getOrDefault(bot, Integer.valueOf(0))).intValue() + 1));
    List<ItemStack> pearls = getPearls(bot);
    PearlSettings pearlSettings = bot.getSettingManager().getPearlSettings();
    if (pearls.size() <= 0)
      return; 
    if (bot.getFleeManager().getOffTicks() < 10)
      return; 
    if (bot.getHealthManager().getHealth() < bot.getMovements().getTargetLocate().getTargetEntity().getHealth())
      return; 
    if (getAngle(bot) < pearlSettings.getAngleDifference().getIntValue())
      return; 
    if (((Integer)this.pearlTick.get(bot)).intValue() < pearlSettings.getPearlDelay().getIntValue())
      return; 
    pearl(pearls.get(0), bot);
  }
  
  public void pearl(final ItemStack pearl, final Bot bot) {
    this.pearlTick.put(bot, Integer.valueOf(0));
    bot.getInventoryManager().setMainHand(itemSlot(pearl, bot));
    (new BukkitRunnable() {
        public void run() {
          if (!bot.isAlive())
            return; 
          Location location = bot.getMovements().getLocation();
          location.setYaw(location.getYaw() + bot.getSettingManager().getPearlSettings().getPearlThrowYawOffSet().getIntValue());
          location.setPitch(3.0F);
          Pearling.this.updateLocation(bot, location);
          bot.getInventoryManager().setMainHand(1);
          if (Pearling.this.getAngle(bot) < bot.getSettingManager().getPearlSettings().getAngleDifference().getIntValue())
            return; 
          bot.getInventoryManager().removeItem(Pearling.this.itemSlot(pearl, bot));
          bot.getNpc().getBukkitEntity().launchProjectile(EnderPearl.class);
        }
      }).runTaskLater((Plugin)main.getInstance(), bot.getSettingManager().getPearlSettings().getPearlingTicks().getIntValue());
  }
  
  public void updateLocation(Bot bot, Location location) {
    bot.getMovements().setLocation(location);
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    float yaw = location.getYaw();
    float pitch = location.getPitch();
    bot.getNpc().setLocation(x, y, z, yaw, pitch);
  }
  
  @EventHandler
  public void teleport(final ProjectileLaunchEvent e) {
    if (!e.getEntity().getType().equals(EntityType.ENDER_PEARL))
      return; 
    if (e.getEntity().getShooter() instanceof LivingEntity) {
      LivingEntity shooter = (LivingEntity)e.getEntity().getShooter();
      final Bot bot = main.getInstance().getBotManager().getNpcFromId(shooter.getEntityId());
      if (bot == null)
        return; 
      (new BukkitRunnable() {
          Location trackedLoc = e.getEntity().getLocation();
          
          public void run() {
            if (e.getEntity().isDead()) {
              Pearling.this.updateLocation(bot, this.trackedLoc);
              cancel();
            } else {
              this.trackedLoc = e.getEntity().getLocation();
            } 
          }
        }).runTaskTimer((Plugin)main.getInstance(), 1L, 1L);
    } 
  }
  
  public int itemSlot(ItemStack stack, Bot bot) {
    return bot.getInventoryManager().getItemSlot(stack);
  }
  
  public float getAngle(Bot bot) {
    Location from = bot.getMovements().getLocation();
    Location to = bot.getMovements().getTargetLocate().getTargetEntity().getLocation();
    float yaw = (float)calculateYawDifference(to, from).getX();
    yaw -= to.getYaw();
    if (yaw > 180.0F)
      yaw -= 360.0F; 
    return Math.abs(yaw);
  }
  
  public static Vector calculateYawDifference(Location from, Location to) {
    Location clonedFrom = from.clone();
    Vector startVector = clonedFrom.toVector();
    Vector targetVector = to.toVector();
    clonedFrom.setDirection(targetVector.subtract(startVector));
    return new Vector(clonedFrom.getYaw(), 0.0F, clonedFrom.getPitch());
  }
  
  public List<ItemStack> getPearls(Bot bot) {
    return bot.getInventoryManager().hasItem(Material.ENDER_PEARL);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\Pearling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */