package mc.apptoeat.com.bot.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class EntityUtils {
  public static boolean isProjectile(@NotNull Entity entity) {
    if (entity.getType() == EntityType.SNOWBALL)
      return true; 
    if (entity.getType() == EntityType.EGG)
      return true; 
    if (entity.getType() == EntityType.FISHING_HOOK)
      return true; 
    return (entity.getType() == EntityType.ARROW);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\EntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */