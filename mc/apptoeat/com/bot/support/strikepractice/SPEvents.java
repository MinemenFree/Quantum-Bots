package mc.apptoeat.com.bot.support.strikepractice;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.battlekit.BattleKit;
import ga.strikepractice.events.BotDuelEndEvent;
import ga.strikepractice.events.BotDuelStartEvent;
import ga.strikepractice.events.PlayerStartSpectatingEvent;
import ga.strikepractice.events.PlayerStopSpectatingEvent;
import ga.strikepractice.events.RoundStartEvent;
import ga.strikepractice.fights.Fight;
import ga.strikepractice.fights.botduel.BotDuel;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import mc.apptoeat.com.bot.botevents.events.BotDeathEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.mechanics.InventoryManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Nms;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class SPEvents implements Listener {
  private final StrikePracticeSupport support;
  
  public StrikePracticeSupport getSupport() {
    return this.support;
  }
  
  public SPEvents(StrikePracticeSupport support) {
    this.support = support;
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  @EventHandler
  public void botDuelStartEvent(BotDuelStartEvent e) throws IOException {
    Location loc = e.getBot().getStoredLocation();
    BattleKit kit = e.getFight().getKit();
    List<ItemStack> inv = kit.getInventory();
    ItemStack helmet = kit.getHelmet();
    ItemStack chestPlate = kit.getChestplate();
    ItemStack leggings = kit.getLeggings();
    ItemStack boots = kit.getBoots();
    Collection<PotionEffect> effects = kit.getPotions();
    boolean combo = kit.isCombo();
    if (e.getBot() != null) {
      if (e.getBot() != null && e.getBot().getEntity() != null) {
        e.getBot().getEntity().remove();
        e.getBot().despawn();
        e.getBot().destroy();
      } 
      if (e.getBot() instanceof CraftPlayer)
        removeNPCPacket(((CraftPlayer)e.getBot()).getHandle()); 
    } 
    this.support.getManager().spawn(loc, inv, helmet, chestPlate, leggings, boots, effects, combo, e.getPlayer(), e.getFight().getKit().isBoxing(), e.getFight());
  }
  
  @EventHandler
  public void botDuelStartEvent(final RoundStartEvent e) {
    if (e.getFight() instanceof BotDuel) {
      final BotDuel duel = (BotDuel)e.getFight();
      final Player player = Bukkit.getPlayer(duel.getP1());
      Bot bot = main.getInstance().getBotManager().getNpcFromTarget(player);
      if (bot != null)
        bot.silentKill(); 
      if (e.getBestOf().getRounds() <= 1)
        return; 
      if (e.getBestOf().getCurrentRound() <= 1)
        return; 
      (new BukkitRunnable() {
          public void run() {
            try {
              SPEvents.this.spawnBot(duel, player, e.getFight().getArena().getLoc2());
            } catch (Throwable $ex) {
              throw $ex;
            } 
          }
        }).runTaskLater((Plugin)main.getInstance(), 1L);
    } 
  }
  
  public void spawnBot(BotDuel fight, Player player, Location loc) throws IOException {
    BattleKit kit = fight.getKit();
    List<ItemStack> inv = kit.getInventory();
    ItemStack helmet = kit.getHelmet();
    ItemStack chestPlate = kit.getChestplate();
    ItemStack leggings = kit.getLeggings();
    ItemStack boots = kit.getBoots();
    Collection<PotionEffect> effects = kit.getPotions();
    boolean combo = kit.isCombo();
    fight.getP2().getBukkitEntity().remove();
    fight.getP2().getNPC().despawn();
    fight.getP2().getNPC().destroy();
    fight.getP2().destroy();
    if (fight.getP2().getBukkitEntity() instanceof CraftPlayer)
      removeNPCPacket(((CraftPlayer)fight.getP2().getBukkitEntity()).getHandle()); 
    Bot bot = this.support.getManager().spawn(loc, inv, helmet, chestPlate, leggings, boots, effects, combo, player, fight.getKit().isBoxing(), fight);
    if (bot == null)
      return; 
    fight.getSpectators().forEach(player1 -> bot.getViewsManager().addPlayer(player1));
  }
  
  @EventHandler
  public void botDeathEvent(final BotDeathEvent e) {
    final Player target = e.getBot().getMovements().getTargetLocate().getTargetEntity();
    (new BukkitRunnable() {
        public void run() {
          Fight fight = StrikePractice.getAPI().getFight(target);
          if (fight instanceof BotDuel) {
            BotDuel botDuel = (BotDuel)fight;
            if (botDuel.getP2() == null)
              return; 
            Bot bot = e.getBot();
            InventoryManager inv = bot.getInventoryManager();
            for (Integer slot : inv.slots.keySet())
              botDuel.getP2().getBukkitEntity().getInventory().setItem(slot.intValue() - 1, (ItemStack)inv.slots.get(slot)); 
            botDuel.handleBotDeath((Metadatable)botDuel.getP2().getBukkitEntity());
          } 
        }
      }).runTask((Plugin)StrikePractice.getInstance());
  }
  
  @EventHandler
  public void botEndEvent(BotDuelEndEvent e) {
    Bot bot = main.getInstance().getBotManager().getNpcFromTarget(e.getPlayer());
    if (bot == null)
      return; 
    int id = bot.getNpc().getBukkitEntity().getEntityId();
    e.getFight().getP2().getBukkitEntity().getInventory().clear();
    InventoryManager inv = bot.getInventoryManager();
    for (Integer slot : inv.slots.keySet())
      e.getFight().getP2().getBukkitEntity().getInventory().setItem(slot.intValue() - 1, (ItemStack)inv.slots.get(slot)); 
    this.support.getManager().kill(id);
  }
  
  @EventHandler
  public void spec(PlayerStartSpectatingEvent e) {
    Player player = e.getPlayer();
    e.getFight().getPlayersInFight().forEach(p -> {
          Bot bot = main.getInstance().getBotManager().getNpcFromTarget(p);
          if (bot != null)
            bot.getViewsManager().addPlayer(player); 
        });
  }
  
  @EventHandler
  public void spec(PlayerStopSpectatingEvent e) {
    Player player = e.getPlayer();
    e.getFight().getPlayersInFight().forEach(p -> {
          Bot bot = main.getInstance().getBotManager().getNpcFromTarget(p);
          if (bot != null)
            bot.getViewsManager().removePlayer(player); 
        });
  }
  
  public void removeNPCPacket(EntityPlayer entity) {
    for (Player player : Bukkit.getOnlinePlayers()) {
      Nms.sendPacket(player, (Packet)new PacketPlayOutEntityDestroy(new int[] { entity.getId() }));
    } 
    for (Player player : Bukkit.getOnlinePlayers()) {
      Nms.sendPacket(player, (Packet)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { entity }));
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\strikepractice\SPEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */