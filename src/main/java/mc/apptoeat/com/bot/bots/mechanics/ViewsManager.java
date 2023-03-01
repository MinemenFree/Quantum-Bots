package mc.apptoeat.com.bot.bots.mechanics;

import java.util.List;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import org.bukkit.entity.Player;

public class ViewsManager extends Manager {
  private final List<Player> players;
  
  public List<Player> getPlayers() {
    return this.players;
  }
  
  public ViewsManager(Bot bot, List<Player> players) {
    super(bot);
    this.players = players;
  }
  
  public void addPlayer(Player player) {
    this.players.add(player);
    getBot().getPacketsManager().sendCreatesPackets(player);
    getBot().getInventoryManager().updateArmorPacket(0);
    getBot().getInventoryManager().updateArmorPacket(1);
    getBot().getInventoryManager().updateArmorPacket(2);
    getBot().getInventoryManager().updateArmorPacket(3);
    getBot().getInventoryManager().updateItemPacket(this.bot.getInventoryManager().getItemInMainHand());
    addPlayerToList(player);
  }
  
  public void removePlayer(Player player) {
    if (this.players.contains(player)) {
      this.players.remove(player);
      getBot().getPacketsManager().sendRemovePackets(player);
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\ViewsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */