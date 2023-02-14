package mc.apptoeat.com.bot;

import java.io.IOException;
import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.EventManager;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.BotManager;
import mc.apptoeat.com.bot.data.DataManager;
import mc.apptoeat.com.bot.data.DataPlayer;
import mc.apptoeat.com.bot.data.presets.PresetManager;
import mc.apptoeat.com.bot.features.FeatureManager;
import mc.apptoeat.com.bot.support.SupportManager;
import mc.apptoeat.com.bot.utils.BukkitEvents;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.Command;
import mc.apptoeat.com.bot.utils.PacketListener;
import mc.apptoeat.com.bot.utils.config.ConfigManager;
import mc.apptoeat.com.bot.utils.gui.imple.BotGui;
import mc.apptoeat.com.bot.utils.settings.DifficultiesSettings;
import mc.apptoeat.com.bot.utils.sql.SQLInfo;
import mc.apptoeat.com.bot.utils.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {
  private static main instance;
  
  DifficultiesSettings difficultiesSettings;
  
  private BotManager botManager;
  
  private DataManager dataManager;
  
  private EventManager eventManager;
  
  private FeatureManager featureManager;
  
  private String pluginName;
  
  private PresetManager presetManager;
  
  private BukkitEvents bukkitEvents;
  
  private SupportManager supportManager;
  
  private SQLManager sqlManager;
  
  private SQLInfo sqlInfo;
  
  public static main getInstance() {
    return instance;
  }
  
  public DifficultiesSettings getDifficultiesSettings() {
    return this.difficultiesSettings;
  }
  
  public BotManager getBotManager() {
    return this.botManager;
  }
  
  public DataManager getDataManager() {
    return this.dataManager;
  }
  
  public EventManager getEventManager() {
    return this.eventManager;
  }
  
  public FeatureManager getFeatureManager() {
    return this.featureManager;
  }
  
  public String getPluginName() {
    return this.pluginName;
  }
  
  public PresetManager getPresetManager() {
    return this.presetManager;
  }
  
  public BukkitEvents getBukkitEvents() {
    return this.bukkitEvents;
  }
  
  public SupportManager getSupportManager() {
    return this.supportManager;
  }
  
  public SQLManager getSqlManager() {
    return this.sqlManager;
  }
  
  public SQLInfo getSqlInfo() {
    return this.sqlInfo;
  }
  
  public void onEnable() {
    instance = this;
    this.pluginName = "OptimizedBotsV2";
    new ConfigManager();
    loadSQL();
    this.botManager = new BotManager();
    this.eventManager = new EventManager();
    this.supportManager = new SupportManager();
    this.featureManager = new FeatureManager();
    this.presetManager = new PresetManager();
    this.bukkitEvents = new BukkitEvents();
    this.difficultiesSettings = new DifficultiesSettings();
    botGuiRegister();
    registerListeners();
    Bukkit.getLogger().info(Color.code("&b&LLoading " + this.pluginName + " &7by crafticat."));
  }
  
  public void loadSQL() {
    this.sqlManager = new SQLManager();
    if (this.sqlManager.isEnabled()) {
      try {
        this.sqlManager.connect();
        Bukkit.getLogger().info(Color.code("&aDatabase is connected"));
      } catch (ClassNotFoundException|java.sql.SQLException ignore) {
        Bukkit.getLogger().info(Color.code("&cDatabase is not connected"));
      } 
      this.sqlInfo = new SQLInfo(this.sqlManager);
    } 
  }
  
  public void botGuiRegister() {
    Command command = new Command("botgui", "Bots setting gui", "/botgui", null);
    command.setOnCommand(player -> {
          DataPlayer data = (getInstance()).dataManager.getPlayer(player);
          BotGui gui = data.getGui();
          if (gui.isLoaded()) {
            gui.getMainGui().openGui();
          } else {
            player.sendMessage(Color.code("&cError: The menu has not loaded, please try again later."));
          } 
        });
  }
  
  public void registerListeners() {
    Bukkit.getServer().getPluginManager().registerEvents((Listener)new PacketListener(), (Plugin)this);
    Bukkit.getServer().getPluginManager().registerEvents((Listener)(this.dataManager = new DataManager()), (Plugin)this);
  }
  
  public void onDisable() {
    for (Bot bot : getBotManager().getBots())
      bot.silentKill(); 
    instance = null;
  }
  
  public void callEvent(Event event) {
    for (BotEvent events : getInstance().getEventManager().getEvents())
      events.call(event); 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */