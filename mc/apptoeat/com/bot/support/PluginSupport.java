package mc.apptoeat.com.bot.support;

import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PluginSupport implements Listener {
  private final String name;
  
  protected Object api;
  
  public String getName() {
    return this.name;
  }
  
  public Object getApi() {
    return this.api;
  }
  
  public PluginSupport(String name) {
    this.name = name;
    this.api = null;
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  public PluginSupport enable() {
    Bukkit.getLogger().info(Color.code("&f" + main.getInstance().getPluginName() + " added support for &b&l" + this.name));
    return this;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\PluginSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */