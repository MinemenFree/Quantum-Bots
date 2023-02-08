package mc.apptoeat.com.bot.support;

import java.util.ArrayList;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.support.papi.PlaceHolderApiSupport;
import mc.apptoeat.com.bot.support.strikepractice.StrikePracticeSupport;
import mc.apptoeat.com.bot.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class SupportManager implements Listener {
  private final ArrayList<String> messages = new ArrayList<>();
  
  public ArrayList<String> getMessages() {
    return this.messages;
  }
  
  private final ArrayList<PluginSupport> pluginSupports = new ArrayList<>();
  
  public ArrayList<PluginSupport> getPluginSupports() {
    return this.pluginSupports;
  }
  
  public SupportManager() {
    addSupport((PluginSupport)new StrikePracticeSupport("aiPractice"));
    addSupport((PluginSupport)new PlaceHolderApiSupport("PlaceHolderAPI"));
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  @EventHandler
  public void joinMessage(PlayerJoinEvent e) {
    this.messages.forEach(message -> e.getPlayer().sendMessage(message));
  }
  
  public void addSupport(PluginSupport plugin) {
    if (Bukkit.getServer().getPluginManager().isPluginEnabled(plugin.getName())) {
      this.pluginSupports.add(plugin.enable());
    } else {
      Bukkit.getLogger().info(Color.code("&f" + main.getInstance().getPluginName() + " were unable to add support for &B&l" + plugin.getName()));
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\SupportManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */