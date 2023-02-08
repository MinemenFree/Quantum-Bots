package mc.apptoeat.com.bot.bots.mechanics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import mc.apptoeat.com.bot.botevents.Event;
import mc.apptoeat.com.bot.botevents.events.BotRequiredToEatEvent;
import mc.apptoeat.com.bot.botevents.events.EatingCancelEvent;
import mc.apptoeat.com.bot.botevents.events.FinishEatingEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Nms;
import mc.apptoeat.com.bot.utils.objects.BooleanRunnable;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentDurability;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryManager extends Manager {
  public Map<Integer, ItemStack> slots;
  
  public void setSlots(Map<Integer, ItemStack> slots) {
    this.slots = slots;
  }
  
  public void setLines(int lines) {
    this.lines = lines;
  }
  
  public void setHelmet(ItemStack helmet) {
    this.helmet = helmet;
  }
  
  public void setChestPlate(ItemStack chestPlate) {
    this.chestPlate = chestPlate;
  }
  
  public void setLeggings(ItemStack leggings) {
    this.leggings = leggings;
  }
  
  public void setBoots(ItemStack boots) {
    this.boots = boots;
  }
  
  public void setBot(Bot bot) {
    this.bot = bot;
  }
  
  public void setLastItem(ItemStack lastItem) {
    this.lastItem = lastItem;
  }
  
  public void setRequiredToEat(boolean requiredToEat) {
    this.requiredToEat = requiredToEat;
  }
  
  public void setAllowToEat(boolean allowToEat) {
    this.allowToEat = allowToEat;
  }
  
  public void setEating(boolean eating) {
    this.eating = eating;
  }
  
  public Map<Integer, ItemStack> getSlots() {
    return this.slots;
  }
  
  private int lines = 4;
  
  private ItemStack helmet;
  
  private ItemStack chestPlate;
  
  private ItemStack leggings;
  
  private ItemStack boots;
  
  private Bot bot;
  
  public int getLines() {
    return this.lines;
  }
  
  public ItemStack getHelmet() {
    return this.helmet;
  }
  
  public ItemStack getChestPlate() {
    return this.chestPlate;
  }
  
  public ItemStack getLeggings() {
    return this.leggings;
  }
  
  public ItemStack getBoots() {
    return this.boots;
  }
  
  public Bot getBot() {
    return this.bot;
  }
  
  private int mainHand = 1;
  
  private ItemStack lastItem;
  
  private boolean requiredToEat;
  
  private boolean allowToEat;
  
  private boolean eating;
  
  public int getMainHand() {
    return this.mainHand;
  }
  
  public ItemStack getLastItem() {
    return this.lastItem;
  }
  
  public boolean isRequiredToEat() {
    return this.requiredToEat;
  }
  
  public ItemStack[] getArmorStacks() {
    ItemStack[] result = new ItemStack[4];
    result[0] = convertToNms(this.helmet);
    result[1] = convertToNms(this.chestPlate);
    result[2] = convertToNms(this.leggings);
    result[3] = convertToNms(this.boots);
    return result;
  }
  
  public ItemStack[] getItemStack() {
    ItemStack[] result = new ItemStack[36];
    for (Integer slot : this.slots.keySet())
      result[slot.intValue() - 1] = CraftItemStack.asNMSCopy(this.slots.get(slot)); 
    return result;
  }
  
  public void damageArmor(int index, double damage) {
    ItemStack armor = this.helmet;
    if (index == 1) {
      armor = this.boots;
    } else if (index == 2) {
      armor = this.leggings;
    } else if (index == 3) {
      armor = this.chestPlate;
    } 
    int level = EnchantmentManager.getEnchantmentLevel(Enchantment.DURABILITY.id, convertToNms(armor));
    int k = 0;
    Random random = new Random();
    for (int l = 0; level > 0 && l < damage; l++) {
      if (EnchantmentDurability.a(convertToNms(armor), level, random))
        k++; 
    } 
    damage = Math.max(damage - k, 0.0D);
    short durability = (short)(int)(armor.getDurability() - damage);
    armor.setDurability(durability);
    if (durability < 0)
      breakArmor(index); 
    Bukkit.broadcastMessage("damage " + durability);
  }
  
  public void breakArmor(int index) {}
  
  public void damageArmors(double damage) {
    List<ItemStack> armors = new ArrayList<>();
    armors.add(this.boots);
    armors.add(this.leggings);
    armors.add(this.chestPlate);
    armors.add(this.helmet);
    int index = 4;
    for (ItemStack armor : armors) {
      if (armor == null)
        continue; 
      short durability = (short)(int)(armor.getDurability() - damage);
      armor.setDurability(durability);
      if (durability < 0)
        breakArmor(index); 
      index--;
    } 
  }
  
  public ItemStack convertToNms(ItemStack stack) {
    return CraftItemStack.asNMSCopy(stack);
  }
  
  public void setMainHand(int hand) {
    this.mainHand = hand;
    itemUpdate();
  }
  
  public InventoryManager(Bot bot) {
    super(bot);
    this.allowToEat = true;
    this.slots = new HashMap<>();
    this.bot = bot;
  }
  
  public ItemStack getItemInMainHand() {
    return this.slots.get(Integer.valueOf(this.mainHand));
  }
  
  public void setItem(int slot, ItemStack item) {
    if (slot > this.lines * 9)
      return; 
    if (item == null)
      return; 
    if (item.getType().equals(Material.AIR))
      return; 
    this.slots.put(Integer.valueOf(slot), item);
    itemUpdate();
  }
  
  public void addItem(ItemStack item) {
    for (int slot = 1; slot <= this.lines * 9; this.lines++) {
      if (!this.slots.containsKey(Integer.valueOf(slot))) {
        setItem(slot, item);
        break;
      } 
    } 
    itemUpdate();
  }
  
  public void removeItem(int slot) {
    ItemStack stack = this.slots.get(Integer.valueOf(slot));
    if (stack == null)
      return; 
    if (stack.getAmount() > 1) {
      stack.setAmount(stack.getAmount() - 1);
      return;
    } 
    this.slots.put(Integer.valueOf(slot), null);
    itemUpdate();
  }
  
  public void deleteItem(int slot) {
    this.slots.put(Integer.valueOf(slot), null);
    itemUpdate();
  }
  
  public void applyItemList(List<ItemStack> items) {
    int counter = 0;
    for (ItemStack item : items) {
      if (item == null) {
        setItem(counter + 1, (ItemStack)null);
      } else {
        setItem(counter + 1, item.clone());
      } 
      counter++;
    } 
  }
  
  public List<ItemStack> hasItem(Material material) {
    List<ItemStack> list = new ArrayList<>();
    this.slots.values().forEach(value -> {
          if (value != null && value.getType() == material)
            list.add(value); 
        });
    return list;
  }
  
  public List<ItemStack> hasArmor(int index) {
    List<ItemStack> list = new ArrayList<>();
    this.slots.values().forEach(value -> {
          if (value != null && hasArmor(index, value.getType()))
            list.add(value); 
        });
    return list;
  }
  
  public boolean hasArmor(int index, Material material) {
    if (index == 4)
      return isBoots(material); 
    if (index == 3)
      return isChestplate(material); 
    if (index == 2)
      return isLeggings(material); 
    if (index == 1)
      return isHelmet(material); 
    return false;
  }
  
  public void addArmor(int index, ItemStack stack) {
    if (index == 1)
      updateBoots(stack); 
    if (index == 2)
      updateLeggings(stack); 
    if (index == 3)
      updateChestPlate(stack); 
    if (index == 4)
      updateHelmet(stack); 
  }
  
  public boolean isBoots(Material material) {
    if (material == null)
      return false; 
    if (material == Material.CHAINMAIL_BOOTS)
      return true; 
    if (material == Material.DIAMOND_BOOTS)
      return true; 
    if (material == Material.IRON_BOOTS)
      return true; 
    if (material == Material.GOLD_BOOTS)
      return true; 
    if (material == Material.LEATHER_BOOTS)
      return true; 
    return false;
  }
  
  public boolean isChestplate(Material material) {
    if (material == null)
      return false; 
    if (material == Material.CHAINMAIL_CHESTPLATE)
      return true; 
    if (material == Material.DIAMOND_CHESTPLATE)
      return true; 
    if (material == Material.IRON_CHESTPLATE)
      return true; 
    if (material == Material.GOLD_CHESTPLATE)
      return true; 
    if (material == Material.LEATHER_CHESTPLATE)
      return true; 
    return false;
  }
  
  public boolean isLeggings(Material material) {
    if (material == null)
      return false; 
    if (material == Material.CHAINMAIL_LEGGINGS)
      return true; 
    if (material == Material.DIAMOND_LEGGINGS)
      return true; 
    if (material == Material.IRON_LEGGINGS)
      return true; 
    if (material == Material.GOLD_LEGGINGS)
      return true; 
    if (material == Material.LEATHER_LEGGINGS)
      return true; 
    return false;
  }
  
  public boolean isHelmet(Material material) {
    if (material == null)
      return false; 
    if (material == Material.CHAINMAIL_HELMET)
      return true; 
    if (material == Material.DIAMOND_HELMET)
      return true; 
    if (material == Material.IRON_HELMET)
      return true; 
    if (material == Material.GOLD_HELMET)
      return true; 
    if (material == Material.LEATHER_HELMET)
      return true; 
    return false;
  }
  
  public List<ItemStack> hasItem(int material) {
    List<ItemStack> list = new ArrayList<>();
    this.slots.values().forEach(value -> {
          if (value != null && value.getTypeId() == material)
            list.add(value); 
        });
    return list;
  }
  
  public void applyInv(Inventory inventory) {
    for (int slot = 0; slot < this.lines * 9; this.lines++) {
      ItemStack stack = inventory.getItem(slot);
      if (stack != null)
        setItem(slot + 1, stack); 
    } 
    itemUpdate();
  }
  
  public void itemUpdate() {
    ItemStack updated = getItemInMainHand();
    ItemStack last = this.lastItem;
    this.lastItem = updated;
    if (last != updated)
      updateItemPacket(updated); 
  }
  
  public void updateHelmet(ItemStack helmet) {
    this.helmet = helmet;
    if (helmet != null)
      updateItemPacket(helmet, 1); 
  }
  
  public void updateChestPlate(ItemStack chestPlate) {
    this.chestPlate = chestPlate;
    if (chestPlate != null)
      updateItemPacket(chestPlate, 2); 
  }
  
  public void updateLeggings(ItemStack leggings) {
    this.leggings = leggings;
    if (leggings != null)
      updateItemPacket(leggings, 3); 
  }
  
  public void updateBoots(ItemStack boots) {
    this.boots = boots;
    if (boots != null)
      updateItemPacket(boots, 4); 
  }
  
  public void eat(ItemStack item) {
    if (this.eating)
      return; 
    if (item.getType().equals(Material.POTION)) {
      Collection<PotionEffect> effects = Potion.fromItemStack(item).getEffects();
      if (Potion.fromItemStack(item).isSplash())
        return; 
      if (this.bot.getEffectsManager().hasEffects(effects))
        return; 
      eatEffects(effects, item, true);
      return;
    } 
    if (item.getType().equals(Material.GOLDEN_APPLE)) {
      Collection<PotionEffect> effects = new ArrayList<>();
      Bukkit.broadcastMessage("a gapple was eaten");
      if (item.getData().getData() == 1) {
        effects.add(new PotionEffect(PotionEffectType.REGENERATION, 600, 5));
        effects.add(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1));
        effects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 1));
        effects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 1));
      } else {
        effects.add(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1));
        effects.add(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
      } 
      eatEffects(effects, item, false);
    } 
  }
  
  public void tickPre() {
    PlayerInventory inventory = (this.bot.getNpc()).inventory;
    inventory.armor = getArmorStacks();
    inventory.items = getItemStack();
    if (this.requiredToEat)
      callEvent((Event)new BotRequiredToEatEvent(this.bot)); 
  }
  
  public BooleanRunnable switchSlowTemp(ItemStack item) {
    int slot = getItemSlot(item);
    setMainHand(slot);
    return a -> {
        setMainHand(1);
        if (a)
          removeItem(slot); 
      };
  }
  
  public boolean isAllowToEat() {
    return this.allowToEat;
  }
  
  public boolean isEating() {
    return this.eating;
  }
  
  public void cancelEating() {
    this.allowToEat = false;
  }
  
  public void allowEating() {
    this.allowToEat = true;
    this.requiredToEat = false;
  }
  
  public void eatEffects(Collection<PotionEffect> effects, ItemStack item, boolean drink) {
    this.bot.getMovements().setUse(true);
    BooleanRunnable f1 = switchSlowTemp(item);
    this.eating = true;
    BooleanRunnable f3 = a -> {
        f1.run(a);
        if (a)
          this.bot.getEffectsManager().addEffects(effects); 
        this.bot.getMovements().setUse(false);
        this.eating = false;
        callEvent((Event)new FinishEatingEvent(this.bot, item));
      };
    eatEffect(f3, item, drink);
  }
  
  public void eatEffect(final BooleanRunnable done, final ItemStack item, final boolean drink) {
    (new BukkitRunnable() {
        int ticks = 0;
        
        final int max = 32;
        
        final int soundTicks = 4;
        
        public void run() {
          try {
            if (!InventoryManager.this.bot.isAlive()) {
              cancel();
              return;
            } 
            if (this.ticks % 4 == 0)
              InventoryManager.this.runEffect(item, drink); 
            if (this.ticks++ > 32 || !InventoryManager.this.allowToEat) {
              cancel();
              done.run(InventoryManager.this.allowToEat);
              if (!InventoryManager.this.allowToEat) {
                InventoryManager.this.callEvent((Event)new EatingCancelEvent(InventoryManager.this.bot));
                InventoryManager.this.allowToEat = true;
              } 
            } 
          } catch (Throwable $ex) {
            throw $ex;
          } 
        }
      }).runTaskTimer((Plugin)main.getInstance(), 0L, 1L);
  }
  
  public void runEffect(ItemStack item, boolean drink) {
    this.bot.getViewsManager().getPlayers().forEach(player -> {
          Sound sound = Sound.EAT;
          if (drink)
            sound = Sound.DRINK; 
          Random random = new Random();
          Location loc = this.bot.getMovements().getLocation();
          if (drink)
            player.playSound(this.bot.getMovements().getLocation(), sound, 0.5F, random.nextFloat() * 0.1F + 0.9F); 
          if (!drink)
            player.playSound(this.bot.getMovements().getLocation(), sound, 0.5F + 0.5F * random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F); 
          if (!drink) {
            Item.getById(item.getTypeId());
            PacketPlayOutWorldParticles particlePack = new PacketPlayOutWorldParticles(EnumParticle.ITEM_CRACK, true, (float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), 0.0F, 0.0F, 0.0F, 1.0F, 0, new int[] { item.getTypeId(), item.getTypeId() });
            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)particlePack);
          } 
        });
  }
  
  public int getItemSlot(ItemStack item) {
    for (Iterator<Integer> iterator = getSlots().keySet().iterator(); iterator.hasNext(); ) {
      int key = ((Integer)iterator.next()).intValue();
      ItemStack stack = this.slots.get(Integer.valueOf(key));
      if (stack != null && stack.equals(item))
        return key; 
    } 
    return 1;
  }
  
  public void addPlayerToList(Player player) {
    updateItemPacket(getItemInMainHand());
    if (this.helmet != null)
      updateItemPacket(this.helmet, 1); 
    if (this.chestPlate != null)
      updateItemPacket(this.chestPlate, 2); 
    if (this.leggings != null)
      updateItemPacket(this.leggings, 3); 
    if (this.boots != null)
      updateItemPacket(this.boots, 4); 
  }
  
  public void updateItemPacket(ItemStack updated) {
    updateItemPacket(updated, 0);
  }
  
  public void updateItemPacket(ItemStack updated, int slot) {
    PacketPlayOutEntityEquipment entityEquipment = new PacketPlayOutEntityEquipment(this.bot.getNpc().getId(), slot, CraftItemStack.asNMSCopy(updated));
    globalAction(player -> Nms.sendPacket(player, (Packet)entityEquipment));
  }
  
  public void updateArmorPacket(int slot) {
    PacketPlayOutEntityEquipment entityEquipment = new PacketPlayOutEntityEquipment(this.bot.getNpc().getId(), slot, getArmorStacks()[slot]);
    globalAction(player -> Nms.sendPacket(player, (Packet)entityEquipment));
  }
  
  public Inventory toBukkitInv(PlayerInventory inventory) {
    inventory.clear();
    for (Integer slot : this.slots.keySet())
      inventory.setItem(slot.intValue() - 1, this.slots.get(slot)); 
    return (Inventory)inventory;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\InventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */