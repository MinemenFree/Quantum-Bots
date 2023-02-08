package mc.apptoeat.com.bot.support.papi;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.fights.Fight;
import ga.strikepractice.fights.botduel.BotDuel;
import ga.strikepractice.fights.duel.Duel;
import ga.strikepractice.npc.CitizensNPC;
import ga.strikepractice.utils.PlayerUtil;
import java.util.UUID;
import java.util.function.Function;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BotStatsPlaceHolders extends PlaceholderExpansion {
  @NotNull
  public String getIdentifier() {
    return "botstats";
  }
  
  @NotNull
  public String getAuthor() {
    return "crafticat";
  }
  
  @NotNull
  public String getVersion() {
    return "1.0";
  }
  
  public boolean canRegister() {
    return true;
  }
  
  public boolean persist() {
    return true;
  }
  
  @Nullable
  public String onPlaceholderRequest(Player player, @NotNull String params) {
    if (player == null)
      return ""; 
    if (params.equals("hits")) {
      DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
      int counter = data.getBotHitCounter();
      Bot bot = main.getInstance().getBotManager().getNpcFromTarget(player);
      if (bot == null) {
        Fight fight = StrikePractice.getAPI().getFight(player);
        counter = defaultHits(player, fight);
      } 
      return counter + "";
    } 
    if (params.equals("hitsdifference")) {
      DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
      int hits = data.getHitCounter();
      int botHits = data.getBotHitCounter();
      int diff = hits - botHits;
      String string = Color.code("&a(+%value%)");
      if (diff < 0)
        string = Color.code("&c(%value%)"); 
      return string.replace("%value%", diff + "");
    } 
    if (params.equals("combo")) {
      DataPlayer data = main.getInstance().getDataManager().getPlayer(player);
      int combo = data.getCombo();
      int botCombo = data.getBotCombo();
      if (combo < 2 && botCombo < 2)
        return "No Combo"; 
      if (combo > botCombo)
        return Color.code("&a") + combo + " Combo"; 
      return Color.code("&c") + botCombo + " Combo";
    } 
    return "";
  }
  
  public int defaultHits(Player player, Fight fight) {
    Function<Fight, String> opponentName = c -> {
        if (fight instanceof Duel) {
          Duel duel = (Duel)c;
          String opponent = duel.getP1();
          if (opponent.equals(player.getName()))
            opponent = duel.getP2(); 
          return opponent;
        } 
        return (c instanceof BotDuel) ? ((BotDuel)c).getBotName() : null;
      };
    String op = opponentName.apply(fight);
    Player p = PlayerUtil.getPlayer(op);
    if (fight == null)
      return -1; 
    if (p != null)
      return fight.getStatistics(p).getHits(); 
    if (fight instanceof BotDuel) {
      CitizensNPC npc = ((BotDuel)fight).getP2();
      if (npc != null && npc.getBukkitEntity() != null) {
        UUID uuid = npc.getBukkitEntity().getUniqueId();
        return fight.getStatistics(uuid).getHits();
      } 
    } 
    return -1;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\papi\BotStatsPlaceHolders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */