package mc.apptoeat.com.bot.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WorldUtils {
  public static Vector calculateDirectionDifference(Location from, Location to) {
    Location clonedFrom = from.clone();
    Vector startVector = clonedFrom.toVector();
    Vector targetVector = to.toVector();
    clonedFrom.setDirection(targetVector.subtract(startVector));
    return new Vector(clonedFrom.getYaw(), 0.0F, clonedFrom.getPitch());
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\WorldUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */