package mc.apptoeat.com.bot.data;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataManager implements Listener {
  public Map<Player, DataPlayer> getDataSet() {
    return this.dataSet;
  }
  
  private final Map<Player, DataPlayer> dataSet = new HashMap<>();
  
  @EventHandler
  public void add(PlayerJoinEvent e) {
    this.dataSet.put(e.getPlayer(), new DataPlayer(e.getPlayer()));
  }
  
  @EventHandler
  public void remove(PlayerQuitEvent e) {
    this.dataSet.put(e.getPlayer(), null);
  }
  
  public DataPlayer getPlayer(Player player) {
    return this.dataSet.get(player);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\data\DataManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */