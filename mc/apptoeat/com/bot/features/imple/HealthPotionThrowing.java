package mc.apptoeat.com.bot.features.imple;

import java.util.List;
import java.util.stream.Collectors;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotHealthChangeEvent;
import mc.apptoeat.com.bot.botevents.events.RunningEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class HealthPotionThrowing extends Feature {
  public void listen(Event event) {
    if (event instanceof BotHealthChangeEvent) {
      BotHealthChangeEvent e = (BotHealthChangeEvent)event;
      Bot bot = e.bot;
      if (e.getAfter() > 4.0D || bot.getHealthManager().getHealth() > bot.getMovements().getTargetLocate().getTargetEntity().getHealth())
        return; 
      List<ItemStack> potions = getPotions(bot);
      if (potions.size() <= 0)
        return; 
      bot.getFleeManager().setRunning(true);
      bot.getInventoryManager().setMainHand(bot.getInventoryManager().getItemSlot(potions.get(0)));
    } 
    if (event instanceof RunningEvent) {
      RunningEvent e = (RunningEvent)event;
      Bot bot = e.bot;
      int ticks = e.getTicks();
      if (ticks == bot.getSettingManager().getPotionSetting().getFirstPotionTick().getIntValue() || (ticks == bot.getSettingManager().getPotionSetting().getSecondPotionTick().getIntValue() && bot.getSettingManager().getPotionSetting().getSecondPotion().isBooleanValue())) {
        List<ItemStack> potions = getPotions(bot);
        bot.getInventoryManager().removeItem(bot.getInventoryManager().getItemSlot(potions.get(0)));
        throwPotion(potions.get(0), bot);
        if (potions.size() > 1)
          bot.getInventoryManager().setMainHand(bot.getInventoryManager().getItemSlot(potions.get(1))); 
        if (potions.size() <= 1) {
          bot.getInventoryManager().setMainHand(1);
          e.setForceEnd(true);
        } 
      } 
      if ((bot.getHealthManager().getHealth() > bot.getSettingManager().getPotionSetting().getFleeHealth().getIntValue() && nearTarget(bot)) || ticks > bot.getSettingManager().getPotionSetting().getFinishPotionTick().getIntValue()) {
        bot.getInventoryManager().setMainHand(1);
        e.setForceEnd(true);
      } 
    } 
  }
  
  public List<ItemStack> getPotions(Bot bot) {
    List<ItemStack> potions = bot.getInventoryManager().hasItem(373);
    return (List<ItemStack>)potions.stream().filter(item -> (Potion.fromItemStack(item).isSplash() && Potion.fromItemStack(item).getType().equals(PotionType.INSTANT_HEAL))).collect(Collectors.toList());
  }
  
  public boolean nearTarget(Bot bot) {
    Location target = bot.getLagCompensatorManager().getClientLocation();
    Location loc = bot.getMovements().getLocation();
    double distance = target.distance(loc);
    return (distance < bot.getSettingManager().getPotionSetting().getCancelPotionDistance().getDoubleValue());
  }
  
  public void throwPotion(ItemStack potion, Bot bot) {
    launchProjectile(potion, null, bot.getNpc().getBukkitEntity());
    bot.getHealthManager().addHealth(8.0D);
  }
  
  public void launchProjectile(ItemStack item, Vector velocity, CraftPlayer entity) {
    WorldServer worldServer = ((CraftWorld)entity.getWorld()).getHandle();
    EntityPotion entityPotion = new EntityPotion((World)worldServer, (EntityLiving)entity.getHandle(), CraftItemStack.asNMSCopy(item));
    Validate.notNull(entityPotion, "Projectile not supported");
    if (velocity != null)
      ((Projectile)entityPotion.getBukkitEntity()).setVelocity(velocity); 
    worldServer.addEntity((Entity)entityPotion);
    entityPotion.getBukkitEntity();
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\HealthPotionThrowing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */