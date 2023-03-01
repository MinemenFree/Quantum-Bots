package mc.apptoeat.com.bot.bots.mechanics;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.npc.NPCEntity;
import mc.apptoeat.com.bot.utils.Nms;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PacketsManager extends Manager {
  private final boolean relativeMovement = this.bot.getSettingManager().getPacketsSettings().getRelativeMove().isBooleanValue();
  
  public boolean isRelativeMovement() {
    return this.relativeMovement;
  }
  
  public PacketsManager(Bot bot) {
    super(bot);
  }
  
  public void sendCreatesPackets() {
    globalAction(this::sendCreatesPackets);
  }
  
  public void sendCreatesPackets(Player player) {
    Nms.sendPacket(player, (Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { (EntityPlayer)getBot().getNpc() }));
    Nms.sendPacket(player, (Packet)new PacketPlayOutNamedEntitySpawn((EntityHuman)getBot().getNpc()));
    Nms.sendPacket(player, (Packet)new PacketPlayOutEntityHeadRotation((Entity)getBot().getNpc(), (byte)(int)((getBot().getNpc()).yaw * 256.0F / 360.0F)));
    skinUpdate(player);
  }
  
  public void sendRemovePackets() {
    getBot().getViewsManager().getPlayers().forEach(this::sendRemovePackets);
  }
  
  public void sendDeathPackets() {
    for (Player player : getBot().getViewsManager().getPlayers())
      Nms.sendPacket(player, (Packet)new PacketPlayOutEntityStatus((Entity)this.bot.getNpc(), (byte)3)); 
  }
  
  public void sendRemovePackets(Player player) {
    Nms.sendPacket(player, (Packet)new PacketPlayOutEntityDestroy(new int[] { getBot().getNpc().getId() }));
    Nms.sendPacket(player, (Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { (EntityPlayer)getBot().getNpc() }));
  }
  
  public void skinUpdate(Player player) {
    DataWatcher watcher = getBot().getNpc().getDataWatcher();
    watcher.watch(10, Byte.valueOf(127));
    PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(getBot().getNpc().getId(), watcher, true);
    Nms.sendPacket(player, (Packet)packet);
  }
  
  public void updateLocation(Location location) {
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    float yaw = location.getYaw();
    float pitch = location.getPitch();
    this.bot.getNpc().setLocation(x, y, z, yaw, pitch);
    globalAction(this::sendUpdateLocationPackets);
  }
  
  public void sendUpdateLocationPackets(Player player) {
    NPCEntity nPCEntity = getBot().getNpc();
    if (!this.relativeMovement)
      Nms.sendPacket(player, (Packet)new PacketPlayOutEntityTeleport((Entity)nPCEntity)); 
    Nms.sendPacket(player, (Packet)new PacketPlayOutEntityHeadRotation((Entity)nPCEntity, (byte)(int)(((EntityPlayer)nPCEntity).yaw * 256.0F / 360.0F)));
  }
  
  public void sendBlockHittingPackets(boolean enabled) {
    PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata();
    DataWatcher.WatchableObject watchableObject = new DataWatcher.WatchableObject(0, 0, Byte.valueOf((byte)(enabled ? 16 : 0)));
    ArrayList<DataWatcher.WatchableObject> list = new ArrayList<>();
    list.add(watchableObject);
    for (Field field : metadata.getClass().getDeclaredFields()) {
      if (field.getName().equals("a")) {
        field.setAccessible(true);
        try {
          field.set(metadata, Integer.valueOf(this.bot.getNpc().getBukkitEntity().getEntityId()));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } 
      } 
      if (field.getName().equals("b")) {
        field.setAccessible(true);
        try {
          field.set(metadata, list);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } 
      } 
    } 
    globalAction(player -> Nms.sendPacket(player, (Packet)metadata));
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\PacketsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */