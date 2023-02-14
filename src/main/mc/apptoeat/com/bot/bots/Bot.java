package mc.apptoeat.com.bot.bots;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotDeathEvent;
import mc.apptoeat.com.bot.botevents.events.SpawnEvent;
import mc.apptoeat.com.bot.bots.events.MEventManager;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.bots.mechanics.AttackingManager;
import mc.apptoeat.com.bot.bots.mechanics.DamageCalculator;
import mc.apptoeat.com.bot.bots.mechanics.DamageManager;
import mc.apptoeat.com.bot.bots.mechanics.EffectsManager;
import mc.apptoeat.com.bot.bots.mechanics.FleeManager;
import mc.apptoeat.com.bot.bots.mechanics.HealthManager;
import mc.apptoeat.com.bot.bots.mechanics.InventoryManager;
import mc.apptoeat.com.bot.bots.mechanics.LagCompensatorManager;
import mc.apptoeat.com.bot.bots.mechanics.PacketsManager;
import mc.apptoeat.com.bot.bots.mechanics.VelocityCalculator;
import mc.apptoeat.com.bot.bots.mechanics.ViewsManager;
import mc.apptoeat.com.bot.bots.mechanics.movementsmanagement.MovementsMain;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.npc.NPCEntity;
import mc.apptoeat.com.bot.npc.NPCNetworkManager;
import mc.apptoeat.com.bot.npc.NPCPlayerConnection;
import mc.apptoeat.com.bot.utils.Nms;
import mc.apptoeat.com.bot.utils.objects.EventRunnable;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Bot extends BotEvent {
  private MovementsMain movements;
  
  public void setMovements(MovementsMain movements) {
    this.movements = movements;
  }
  
  public void setNpc(NPCEntity npc) {
    this.npc = npc;
  }
  
  public void setSettingManager(SettingManager settingManager) {
    this.settingManager = settingManager;
  }
  
  public void setPacketsManager(PacketsManager packetsManager) {
    this.packetsManager = packetsManager;
  }
  
  public void setViewsManager(ViewsManager viewsManager) {
    this.viewsManager = viewsManager;
  }
  
  public void setEffectsManager(EffectsManager effectsManager) {
    this.effectsManager = effectsManager;
  }
  
  public void setVelocityCalculator(VelocityCalculator velocityCalculator) {
    this.velocityCalculator = velocityCalculator;
  }
  
  public void setMEventManager(MEventManager mEventManager) {
    this.MEventManager = mEventManager;
  }
  
  public void setDamageManager(DamageManager damageManager) {
    this.damageManager = damageManager;
  }
  
  public void setInventoryManager(InventoryManager inventoryManager) {
    this.inventoryManager = inventoryManager;
  }
  
  public void setAttackingManager(AttackingManager attackingManager) {
    this.attackingManager = attackingManager;
  }
  
  public void setHealthManager(HealthManager healthManager) {
    this.healthManager = healthManager;
  }
  
  public void setLagCompensatorManager(LagCompensatorManager lagCompensatorManager) {
    this.lagCompensatorManager = lagCompensatorManager;
  }
  
  public void setFleeManager(FleeManager fleeManager) {
    this.fleeManager = fleeManager;
  }
  
  public void setEventListener(EventRunnable eventListener) {
    this.eventListener = eventListener;
  }
  
  public void setDamageCalculator(DamageCalculator damageCalculator) {
    this.damageCalculator = damageCalculator;
  }
  
  public void setAlive(boolean alive) {
    this.alive = alive;
  }
  
  public MovementsMain getMovements() {
    return this.movements;
  }
  
  private static Map<String, Property> registeredSkins = new HashMap<>();
  
  private NPCEntity npc;
  
  private SettingManager settingManager;
  
  private PacketsManager packetsManager;
  
  private ViewsManager viewsManager;
  
  private EffectsManager effectsManager;
  
  private VelocityCalculator velocityCalculator;
  
  private MEventManager MEventManager;
  
  private DamageManager damageManager;
  
  private InventoryManager inventoryManager;
  
  private AttackingManager attackingManager;
  
  private HealthManager healthManager;
  
  private LagCompensatorManager lagCompensatorManager;
  
  private FleeManager fleeManager;
  
  private EventRunnable eventListener;
  
  private DamageCalculator damageCalculator;
  
  public NPCEntity getNpc() {
    return this.npc;
  }
  
  public SettingManager getSettingManager() {
    return this.settingManager;
  }
  
  public PacketsManager getPacketsManager() {
    return this.packetsManager;
  }
  
  public ViewsManager getViewsManager() {
    return this.viewsManager;
  }
  
  public EffectsManager getEffectsManager() {
    return this.effectsManager;
  }
  
  public VelocityCalculator getVelocityCalculator() {
    return this.velocityCalculator;
  }
  
  public MEventManager getMEventManager() {
    return this.MEventManager;
  }
  
  public DamageManager getDamageManager() {
    return this.damageManager;
  }
  
  public InventoryManager getInventoryManager() {
    return this.inventoryManager;
  }
  
  public AttackingManager getAttackingManager() {
    return this.attackingManager;
  }
  
  public HealthManager getHealthManager() {
    return this.healthManager;
  }
  
  public LagCompensatorManager getLagCompensatorManager() {
    return this.lagCompensatorManager;
  }
  
  public FleeManager getFleeManager() {
    return this.fleeManager;
  }
  
  public EventRunnable getEventListener() {
    return this.eventListener;
  }
  
  public DamageCalculator getDamageCalculator() {
    return this.damageCalculator;
  }
  
  private boolean alive = true;
  
  public boolean isAlive() {
    return this.alive;
  }
  
  public Bot(Location loc, String name, String skin, Player p, SettingManager settings) throws IOException, InterruptedException {
    registerNPC(name, loc);
    registerManagers(settings, loc, p);
    registerSkin(skin, p);
    this.packetsManager.sendCreatesPackets();
    loadManagers();
    registerBot();
  }
  
  private void registerBot() {
    main.getInstance().getBotManager().addBot(this);
    main.getInstance().getEventManager().registerEvent(this);
    this.movements.callEvent((Event)new SpawnEvent(this));
  }
  
  private void loadManagers() {
    this.MEventManager.addEvent((Manager)this.movements);
    this.MEventManager.addEvent((Manager)this.viewsManager);
    this.MEventManager.addEvent((Manager)this.inventoryManager);
    this.MEventManager.addEvent((Manager)this.packetsManager);
    this.MEventManager.addEvent((Manager)(this.damageManager = new DamageManager(this)));
    this.MEventManager.addEvent((Manager)(this.attackingManager = new AttackingManager(this)));
    this.MEventManager.addEvent((Manager)(this.healthManager = new HealthManager(this)));
    this.MEventManager.addEvent((Manager)(this.damageCalculator = new DamageCalculator(this)));
    this.MEventManager.addEvent((Manager)this.fleeManager);
    this.MEventManager.addEvent((Manager)this.effectsManager);
  }
  
  private void registerManagers(SettingManager settings, Location loc, Player p) {
    if (settings != null)
      this.settingManager = settings; 
    if (settings == null)
      this.settingManager = new SettingManager(); 
    this.lagCompensatorManager = new LagCompensatorManager(this);
    this.MEventManager = new MEventManager(this);
    this.effectsManager = new EffectsManager(this);
    this.velocityCalculator = new VelocityCalculator(this);
    this.movements = new MovementsMain(this, loc, p);
    this.packetsManager = new PacketsManager(this);
    this.inventoryManager = new InventoryManager(this);
    this.fleeManager = new FleeManager(this);
    List<Player> playerList = new ArrayList<>();
    playerList.add(p);
    this.viewsManager = new ViewsManager(this, playerList);
  }
  
  private void registerNPC(String name, Location loc) {
    GameProfile profile = new GameProfile(UUID.randomUUID(), name);
    MinecraftServer server = ((CraftServer)Bukkit.getServer()).getServer();
    final WorldServer world = ((CraftWorld)loc.getWorld()).getHandle();
    PlayerInteractManager interactManager = new PlayerInteractManager((World)world);
    this.npc = new NPCEntity(server, world, profile, interactManager);
    this.npc.playerConnection = (PlayerConnection)new NPCPlayerConnection(server, (NetworkManager)new NPCNetworkManager(EnumProtocolDirection.CLIENTBOUND), (EntityPlayer)this.npc);
    this.npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    this.npc.setPosition(loc.getX(), loc.getY(), loc.getZ());
    (new BukkitRunnable() {
        public void run() {
          world.addEntity((Entity)Bot.this.npc);
        }
      }).runTask((Plugin)main.getInstance());
  }
  
  private void registerSkin(String name, Player fallback) throws IOException {
    InputStreamReader reader_0;
    try {
      for (Player p : Bukkit.getOnlinePlayers()) {
        if (p.getName().equalsIgnoreCase(name)) {
          Property property1 = Nms.getPlayer(p).getProfile().getProperties().get("textures").iterator().next();
          if (property1 == null)
            continue; 
          this.npc.getProfile().getProperties().put("textures", property1);
          return;
        } 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    if (registeredSkins.containsKey(name)) {
      Property property1 = registeredSkins.get(name);
      this.npc.getProfile().getProperties().put("textures", property1);
      return;
    } 
    try {
      URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
      reader_0 = new InputStreamReader(url_0.openStream());
    } catch (Exception ignore) {
      Bukkit.broadcastMessage("WARNING: Could not load skin " + name + " using fall back skin");
      Property property1 = Nms.getPlayer(fallback).getProfile().getProperties().get("textures").iterator().next();
      if (property1 == null) {
        Bukkit.broadcastMessage("WARNING: could not use fall back skin");
        return;
      } 
      this.npc.getProfile().getProperties().put("textures", property1);
      return;
    } 
    String var1 = (new JsonParser()).parse(reader_0).getAsJsonObject().get("id").getAsString();
    URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + var1 + "?unsigned=false");
    InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
    JsonObject textureProperty = (new JsonParser()).parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
    String texture = textureProperty.get("value").getAsString();
    String signature = textureProperty.get("signature").getAsString();
    Property property = new Property("textures", texture, signature);
    this.npc.getProfile().getProperties().put("textures", property);
    registeredSkins.put(name, property);
  }
  
  public void kill() {
    this.attackingManager.setAttacking(false);
    Bukkit.broadcastMessage("kill");
    deathEvent(() -> {
        
        });
  }
  
  public void silentKill() {
    this.alive = false;
    main.getInstance().getBotManager().removeBot(this);
    this.packetsManager.sendRemovePackets();
    this.npc.getBukkitEntity().remove();
  }
  
  public void deathEvent(Runnable executeAction) {
    fakeDeath((Player)this.npc.getBukkitEntity(), this.movements.getTargetLocate().getTargetEntity(), executeAction);
    Bukkit.getServer().getPluginManager().callEvent((Event)new BotDeathEvent(this));
  }
  
  public void fakeDeath(Player player, Player killer, Runnable finishAction) {
    this.velocityCalculator.setVelocity(killer.getLocation().getDirection().normalize().multiply(0.3D).setY(0));
    this.movements.setMove(false);
    this.packetsManager.sendDeathPackets();
    Bukkit.getScheduler().runTaskLater((Plugin)main.getInstance(), () -> {
          this.packetsManager.sendRemovePackets();
          main.getInstance().getBotManager().removeBot(this);
          this.alive = false;
          this.npc.getBukkitEntity().remove();
        }16L);
  }
  
  public void tick() {
    this.MEventManager.tickPre();
    this.MEventManager.tickMid();
    this.MEventManager.tickPost();
    if (!this.movements.getTargetLocate().getTargetEntity().getWorld().equals(this.movements.getLocation().getWorld()))
      return; 
    this.MEventManager.callEvents();
  }
  
  public void listen(Event event) {
    if (event.bot != this)
      return; 
    try {
      if (this.eventListener != null)
        this.eventListener.run(event); 
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\Bot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */