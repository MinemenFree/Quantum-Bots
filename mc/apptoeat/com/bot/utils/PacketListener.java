package mc.apptoeat.com.bot.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import java.io.IOException;
import java.lang.reflect.Field;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.main;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class PacketListener implements Listener {
  @EventHandler
  public void onQuit(PlayerQuitEvent e) {
    removePlayer(e.getPlayer());
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    addPlayer(e.getPlayer());
  }
  
  public void addPlayer(final Player player) {
    ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
        @Deprecated
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
          try {
            if (packet instanceof PacketPlayInUseEntity) {
              PacketPlayInUseEntity wrapper = (PacketPlayInUseEntity)packet;
              PacketListener.this.handleAttack(wrapper, player);
            } 
            if (packet instanceof PacketPlayInFlying) {
              DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
              if (data != null)
                data.handleFlying((PacketPlayInFlying)packet); 
            } 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          super.channelRead(channelHandlerContext, packet);
        }
        
        public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
          DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
          if (packet instanceof PacketPlayOutEntityVelocity) {
            PacketPlayOutEntityVelocity wrapper = (PacketPlayOutEntityVelocity)packet;
            ByteBuf byteBuf = Unpooled.buffer();
            PacketDataSerializer packetData = new PacketDataSerializer(byteBuf);
            wrapper.b(packetData);
            int id = packetData.e();
            double x = packetData.readShort() / 8000.0D;
            double y = packetData.readShort() / 8000.0D;
            double z = packetData.readShort() / 8000.0D;
            if (id == player.getEntityId()) {
              Vector velocity = new Vector(x, y, z);
              if (data.handle()) {
                Vector v = data.velocity();
                packetData = new PacketDataSerializer(byteBuf);
                packetData.b(id);
                packetData.writeShort((int)(v.getX() * 8000.0D));
                packetData.writeShort((int)(v.getY() * 8000.0D));
                packetData.writeShort((int)(v.getZ() * 8000.0D));
                wrapper.a(packetData);
              } 
            } 
          } 
          if (packet instanceof PacketPlayOutEntity) {
            PacketPlayOutEntity wrapper = (PacketPlayOutEntity)packet;
            ByteBuf byteBuf = Unpooled.buffer();
            PacketDataSerializer packetData = new PacketDataSerializer(byteBuf);
            wrapper.b(packetData);
            int id = packetData.e();
            Bot bot = main.getInstance().getBotManager().getNpcFromTargetView(player);
            if (bot != null && bot.getNpc().getId() == id && !bot.getPacketsManager().isRelativeMovement())
              return; 
          } 
          super.write(channelHandlerContext, packet, channelPromise);
        }
      };
    Channel channel = (((CraftPlayer)player).getHandle()).playerConnection.networkManager.channel;
    ChannelPipeline pipeline = channel.pipeline();
    if (pipeline == null)
      return; 
    String handlerName = "bot_processor";
    channel.eventLoop().submit(() -> {
          if (pipeline.get(handlerName) != null)
            pipeline.remove(handlerName); 
          pipeline.addBefore("packet_handler", handlerName, (ChannelHandler)channelDuplexHandler);
          return null;
        });
  }
  
  public PacketDataSerializer getPacketData(Packet wrapper) throws IOException {
    ByteBuf byteBuf = Unpooled.buffer();
    PacketDataSerializer packetData = new PacketDataSerializer(byteBuf);
    wrapper.b(packetData);
    return packetData;
  }
  
  public void handleAttack(PacketPlayInUseEntity wrapper, Player player) throws NoSuchFieldException, IllegalAccessException {
    if (!wrapper.a().equals(PacketPlayInUseEntity.EnumEntityUseAction.ATTACK))
      return; 
    Field f = wrapper.getClass().getDeclaredField("a");
    f.setAccessible(true);
    int id = ((Integer)f.get(wrapper)).intValue();
    attack(id, player);
  }
  
  public void attack(int id, Player attacker) {
    for (Bot bot : main.getInstance().getBotManager().getBotsTick()) {
      if (bot.getNpc().getId() == id);
    } 
  }
  
  public void removePlayer(Player player) {
    Channel channel = (((CraftPlayer)player).getHandle()).playerConnection.networkManager.channel;
    ChannelPipeline pipeline = channel.pipeline();
    String handlerName = "bot_processor";
    channel.eventLoop().submit(() -> {
          if (pipeline.get(handlerName) != null)
            pipeline.remove(handlerName); 
          return null;
        });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\PacketListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */