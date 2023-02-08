package mc.apptoeat.com.bot.npc;

import com.mojang.authlib.GameProfile;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Nms;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class NPCEntity extends EntityPlayer {
  public NPCEntity(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
    super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
  }
  
  protected boolean d(DamageSource damagesource, float f) {
    boolean d = super.d(damagesource, f);
    Bot bot = main.getInstance().getBotManager().getNpcFromId(getId());
    if (bot == null)
      return d; 
    bot.getDamageManager().damage(damagesource, f);
    int fireLevel = EnchantmentManager.getFireAspectEnchantmentLevel((EntityLiving)Nms.getPlayer(bot.getMovements().getTargetLocate().getTargetEntity()));
    if (fireLevel > 0)
      bot.getHealthManager().fire(fireLevel * 4 * 20); 
    return d;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\npc\NPCEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */