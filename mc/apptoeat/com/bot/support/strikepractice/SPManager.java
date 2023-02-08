package mc.apptoeat.com.bot.support.strikepractice;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.fights.botduel.BotDuel;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.mechanics.InventoryManager;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.gui.imple.BotGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class SPManager implements Listener {
  private final StrikePracticeSupport support;
  
  public StrikePracticeSupport getSupport() {
    return this.support;
  }
  
  public SPManager(StrikePracticeSupport support) {
    this.support = support;
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  public Bot spawn(Location loc, List<ItemStack> inv, ItemStack helmet, ItemStack chestPlate, ItemStack leggings, ItemStack boots, Collection<PotionEffect> effects, boolean combo, Player player, boolean noDamage, BotDuel fight) throws IOException {
    DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
    try {
      SettingManager settings = getSettings(data, fight.getDifficulty().toString());
      if (settings == null) {
        StrikePractice.getAPI().cancelFight(player, Color.code("&cError: An error has occured, unable to locate Bot Settings."));
        return null;
      } 
      final Bot bot = new Bot(loc, settings.getBotName().replace("%player%", player.getName()), settings.getSkin().replace("%player%", player.getName()), player, settings);
      if (fight.getKit().getName().contains("sumo"))
        bot.getMovements().setEdgeDetector(true); 
      bot.getDamageManager().setAllowGlobalAttacks(false);
      bot.getMovements().getCollision().setWaterEnterEvent(bot::kill);
      bot.getDamageManager().setNoDamage(noDamage);
      bot.getMovements().setMove(false);
      if (combo)
        bot.getDamageManager().setCombo(combo); 
      if (noDamage) {
        data.setBotHitCounter(0);
        data.setHitCounter(0);
        data.setCombo(0);
        data.setBotCombo(0);
        bot.setEventListener(event -> {
              if (event instanceof mc.apptoeat.com.bot.botevents.events.BotDamageEvent) {
                data.hit();
                fight.getStatistics(player).setHits(data.getHitCounter());
                if (data.getHitCounter() >= 100)
                  bot.kill(); 
              } 
              if (event instanceof mc.apptoeat.com.bot.botevents.events.BotAttackLandEvent) {
                data.botHit();
                if (data.getBotHitCounter() >= 100)
                  player.setHealth(0.0D); 
              } 
            });
      } 
      (new BukkitRunnable() {
          public void run() {
            if (bot.isAlive())
              bot.getMovements().setMove(true); 
          }
        }).runTaskLater((Plugin)main.getInstance(), 120L);
      InventoryManager inventoryManager = bot.getInventoryManager();
      inventoryManager.applyItemList(inv);
      if (helmet != null)
        inventoryManager.updateHelmet(helmet.clone()); 
      if (chestPlate != null)
        inventoryManager.updateChestPlate(chestPlate.clone()); 
      if (leggings != null)
        inventoryManager.updateLeggings(leggings.clone()); 
      if (boots != null)
        inventoryManager.updateBoots(boots.clone()); 
      bot.getEffectsManager().addEffects(effects);
      return bot;
    } catch (InterruptedException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void kill(int id) {
    Bot bot = main.getInstance().getBotManager().getNpcFromId(id);
    if (bot.isAlive())
      bot.silentKill(); 
  }
  
  public SettingManager getSettings(DataPlayer data, String level) {
    if (level.equals("UNRANKED"))
      return main.getInstance().getPresetManager().getUnranked().getPreset().getManager(); 
    if (level.equals("RANKED"))
      return main.getInstance().getPresetManager().getRanked().getPreset().getManager(); 
    BotGui gui = data.getGui();
    if (level.equals("HACKER")) {
      if (!gui.isLoaded()) {
        data.getPlayer().sendMessage(Color.code("&aFetching settings, please a moment..."));
        return null;
      } 
      return data.getGui().getManager();
    } 
    return main.getInstance().getDifficultiesSettings().getData(level);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\strikepractice\SPManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */