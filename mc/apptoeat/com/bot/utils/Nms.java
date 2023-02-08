package mc.apptoeat.com.bot.utils;

import mc.apptoeat.com.bot.utils.objects.AABB;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Nms {
  public static EntityPlayer getPlayer(Player player) {
    return ((CraftPlayer)player).getHandle();
  }
  
  public static void sendPacket(Player player, Packet packet) {
    (getPlayer(player)).playerConnection.sendPacket(packet);
  }
  
  public static Entity getCraftEntity(Entity entity) {
    return ((CraftEntity)entity).getHandle();
  }
  
  public static World getCraftWorld(World world) {
    return (World)((CraftWorld)world).getHandle();
  }
  
  public static AABB entityHitBox(Entity entity) {
    AxisAlignedBB bb = ((CraftEntity)entity).getHandle().getBoundingBox();
    Vector min = new Vector(bb.a, bb.b, bb.c);
    Vector max = new Vector(bb.d, bb.e, bb.f);
    return new AABB(min, max);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\Nms.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */