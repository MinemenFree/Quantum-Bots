package mc.apptoeat.com.bot.npc;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class NPCPlayerConnection extends PlayerConnection {
  public NPCPlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
    super(minecraftserver, networkmanager, entityplayer);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\npc\NPCPlayerConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */