package mc.apptoeat.com.bot.data;

import java.util.ArrayList;
import java.util.List;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.chat.ChatRunnable;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.MathUtils;
import mc.apptoeat.com.bot.utils.gui.imple.BotGui;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DataPlayer implements Listener {
  private final Player player;
  
  private Vector motion;
  
  private BotGui gui;
  
  private int hitCounter;
  
  private int combo;
  
  private int botHitCounter;
  
  private int botCombo;
  
  private ChatRunnable chatRunnable;
  
  private long timeOutChat;
  
  public void setMotion(Vector motion) {
    this.motion = motion;
  }
  
  public void setGui(BotGui gui) {
    this.gui = gui;
  }
  
  public void setHitCounter(int hitCounter) {
    this.hitCounter = hitCounter;
  }
  
  public void setCombo(int combo) {
    this.combo = combo;
  }
  
  public void setBotHitCounter(int botHitCounter) {
    this.botHitCounter = botHitCounter;
  }
  
  public void setBotCombo(int botCombo) {
    this.botCombo = botCombo;
  }
  
  public void setChatRunnable(ChatRunnable chatRunnable) {
    this.chatRunnable = chatRunnable;
  }
  
  public void setTimeOutChat(long timeOutChat) {
    this.timeOutChat = timeOutChat;
  }
  
  public void setSaving(boolean saving) {
    this.saving = saving;
  }
  
  public void setGuiCooldown(int guiCooldown) {
    this.guiCooldown = guiCooldown;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Vector getMotion() {
    return this.motion;
  }
  
  public BotGui getGui() {
    return this.gui;
  }
  
  public int getHitCounter() {
    return this.hitCounter;
  }
  
  public int getCombo() {
    return this.combo;
  }
  
  public int getBotHitCounter() {
    return this.botHitCounter;
  }
  
  public int getBotCombo() {
    return this.botCombo;
  }
  
  public ChatRunnable getChatRunnable() {
    return this.chatRunnable;
  }
  
  public long getTimeOutChat() {
    return this.timeOutChat;
  }
  
  private boolean saving = false;
  
  private int guiCooldown;
  
  private final List<Vector> locations;
  
  public boolean isSaving() {
    return this.saving;
  }
  
  public void botHit() {
    this.botHitCounter++;
    this.botCombo++;
    this.combo = 0;
  }
  
  public void hit() {
    this.hitCounter++;
    this.combo++;
    this.botCombo = 0;
  }
  
  public int getGuiCooldown() {
    return this.guiCooldown;
  }
  
  public List<Vector> getLocations() {
    return this.locations;
  }
  
  public DataPlayer(Player player) {
    this.player = player;
    this.locations = new ArrayList<>();
    this.gui = new BotGui(this);
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  public void handleFlying(PacketPlayInFlying wrapper) {
    Vector posUpdate = new Vector(wrapper.a(), wrapper.b(), wrapper.c());
    handlePos(posUpdate);
  }
  
  public boolean handle() {
    Bot bot = main.getInstance().getBotManager().getNpcFromTarget(this.player);
    if (bot == null)
      return false; 
    long diff = System.currentTimeMillis() - bot.getAttackingManager().getAttacked();
    bot.getAttackingManager().setLandAttack((diff < 150L));
    return (diff < 150L);
  }
  
  public Vector velocity() {
    Bot bot = main.getInstance().getBotManager().getNpcFromTarget(this.player);
    if (bot == null)
      return null; 
    Vector dir = MathUtils.getDirection(bot.getMovements().getLocation().getYaw());
    double h = 0.47D;
    double v = 0.35D;
    double reach = 3.5D;
    double distMultiplier = 0.15D;
    if (bot.getMovements().isAttackSprint() || !bot.getSettingManager().getWTapSetting().getEnabled().isBooleanValue())
      h *= 1.2D; 
    if (this.player.isOnGround())
      h *= 1.1D; 
    double extraH = Math.min(Math.max((reach - bot.getMovements().getLocation().distance(this.player.getLocation()) - 0.3D) * distMultiplier, 0.0D), 0.4D);
    h += extraH;
    double speed = 1.0D;
    for (PotionEffect potionEffect : this.player.getActivePotionEffects()) {
      if (potionEffect.getType().equals(PotionEffectType.SPEED))
        speed = potionEffect.getAmplifier() * 0.2D + 1.0D; 
    } 
    h += 0.13D * speed;
    dir.multiply(h);
    dir.setY(v);
    return dir;
  }
  
  public void handlePos(Vector pos) {
    if (pos.equals(new Vector(0, 0, 0)))
      return; 
    this.locations.add(pos);
    if (this.locations.size() > 3)
      this.locations.remove(0); 
  }
  
  public Vector getLastLoc() {
    if (this.locations.size() == 0)
      return new Vector(0, 0, 0); 
    return this.locations.get(this.locations.size() - 1);
  }
  
  @EventHandler
  public void chat(AsyncPlayerChatEvent e) {
    if (e.getPlayer() == this.player && this.chatRunnable != null && System.currentTimeMillis() - this.timeOutChat < 30000L) {
      if (e.getMessage().length() > 16) {
        this.player.sendMessage(Color.code("&cYou are unable to use skins & names that are longer than 16 characters."));
        e.setCancelled(true);
        this.chatRunnable = null;
        return;
      } 
      e.setCancelled(this.chatRunnable.run(e.getMessage()));
      this.chatRunnable = null;
    } 
  }
  
  public void setChatUpdate(ChatRunnable update) {
    this.chatRunnable = update;
    this.timeOutChat = System.currentTimeMillis();
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\data\DataPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */