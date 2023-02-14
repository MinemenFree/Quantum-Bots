package mc.apptoeat.com.bot.utils;

import mc.apptoeat.com.bot.main;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitEvents {
  private final boolean projectileHitEvent = FileManager.getOrDefault((JavaPlugin)main.getInstance(), "bukkitevents.projectileHitEvent", true);
  
  public boolean isProjectileHitEvent() {
    return this.projectileHitEvent;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\BukkitEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */