package mc.apptoeat.com.bot.utils.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.objects.PlayerRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class Gui implements Listener {
  private final Inventory gui;
  
  private final int size;
  
  private HashMap<ItemStack, PlayerRunnable> clickAction;
  
  private Map<String, PlayerRunnable> nameMap;
  
  private Map<String, PlayerRunnable> nameMap2;
  
  private HashMap<ItemStack, PlayerRunnable> rightClickAction;
  
  public void setClickAction(HashMap<ItemStack, PlayerRunnable> clickAction) {
    this.clickAction = clickAction;
  }
  
  public void setNameMap(Map<String, PlayerRunnable> nameMap) {
    this.nameMap = nameMap;
  }
  
  public void setNameMap2(Map<String, PlayerRunnable> nameMap2) {
    this.nameMap2 = nameMap2;
  }
  
  public void setRightClickAction(HashMap<ItemStack, PlayerRunnable> rightClickAction) {
    this.rightClickAction = rightClickAction;
  }
  
  public void setShiftAction(HashMap<ItemStack, PlayerRunnable> shiftAction) {
    this.shiftAction = shiftAction;
  }
  
  public void setShiftRightAction(HashMap<ItemStack, PlayerRunnable> shiftRightAction) {
    this.shiftRightAction = shiftRightAction;
  }
  
  public Inventory getGui() {
    return this.gui;
  }
  
  public int getSize() {
    return this.size;
  }
  
  public HashMap<ItemStack, PlayerRunnable> getClickAction() {
    return this.clickAction;
  }
  
  public Map<String, PlayerRunnable> getNameMap() {
    return this.nameMap;
  }
  
  public Map<String, PlayerRunnable> getNameMap2() {
    return this.nameMap2;
  }
  
  public HashMap<ItemStack, PlayerRunnable> getRightClickAction() {
    return this.rightClickAction;
  }
  
  private HashMap<ItemStack, PlayerRunnable> shiftAction = new HashMap<>();
  
  public HashMap<ItemStack, PlayerRunnable> getShiftAction() {
    return this.shiftAction;
  }
  
  private HashMap<ItemStack, PlayerRunnable> shiftRightAction = new HashMap<>();
  
  public HashMap<ItemStack, PlayerRunnable> getShiftRightAction() {
    return this.shiftRightAction;
  }
  
  public Gui(String title, int size) {
    this.gui = Bukkit.createInventory(null, size, Color.code(title));
    this.size = size;
    this.clickAction = new HashMap<>();
    this.rightClickAction = new HashMap<>();
    this.nameMap = new HashMap<>();
    this.nameMap2 = new HashMap<>();
    Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin)main.getInstance());
  }
  
  public void reset() {
    this.clickAction = new HashMap<>();
    this.gui.clear();
  }
  
  @EventHandler
  public void click(InventoryClickEvent e) throws IOException {
    Player player = (Player)e.getWhoClicked();
    if (e.getClickedInventory() == null)
      return; 
    if (!e.getClickedInventory().equals(this.gui))
      return; 
    e.setCancelled(true);
    if (e.isLeftClick() && 
      e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
      String name = e.getCurrentItem().getItemMeta().getDisplayName();
      PlayerRunnable runnable = this.nameMap.get(name);
      if (runnable != null) {
        runnable.run(player);
        return;
      } 
    } 
    if (e.isRightClick() && 
      e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
      String name = e.getCurrentItem().getItemMeta().getDisplayName();
      PlayerRunnable runnable = this.nameMap2.get(name);
      if (runnable != null) {
        runnable.run(player);
        return;
      } 
    } 
    if (!e.isShiftClick()) {
      if (e.isLeftClick()) {
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
          return; 
        PlayerRunnable runnable = this.clickAction.get(stack);
        if (runnable == null)
          return; 
        runnable.run(player);
      } 
      if (e.isRightClick()) {
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
          return; 
        PlayerRunnable runnable = this.rightClickAction.get(stack);
        if (runnable == null)
          return; 
        runnable.run(player);
      } 
    } else {
      if (e.isLeftClick()) {
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
          return; 
        PlayerRunnable runnable = this.shiftAction.get(stack);
        if (runnable == null)
          return; 
        runnable.run(player);
      } 
      if (e.isRightClick()) {
        ItemStack stack = e.getCurrentItem();
        if (stack == null)
          return; 
        PlayerRunnable runnable = this.shiftRightAction.get(stack);
        if (runnable == null)
          return; 
        runnable.run(player);
      } 
    } 
  }
  
  public void openInventory(HumanEntity ent) {
    ent.openInventory(this.gui);
  }
  
  public void createGuiItem(Material material, int slot, String name, String color, PlayerRunnable action, String... lore) {
    ItemStack item = new ItemStack(material, 1);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(color + name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    meta.setLore(newLore);
    item.setItemMeta(meta);
    this.clickAction.put(item, action);
    this.gui.setItem(slot, item);
  }
  
  public void createGuiItem(Material material, int slot, String name, String color, PlayerRunnable action, List<String> lore) {
    ItemStack item = new ItemStack(material, 1);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(color + name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    meta.setLore(newLore);
    item.setItemMeta(meta);
    this.clickAction.put(item, action);
    this.gui.setItem(slot, item);
  }
  
  public void createGuiItem(ItemStack stack, int slot, String name, PlayerRunnable action, List<String> lore) {
    ItemStack item = new ItemStack(stack.getType(), stack.getAmount());
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    meta.setLore(newLore);
    item.setItemMeta(meta);
    this.clickAction.put(item, action);
    this.gui.setItem(slot, item);
  }
  
  public void createGuiItem(ItemStack stack, int slot, String name, PlayerRunnable action, PlayerRunnable rightAction, PlayerRunnable shiftAction, PlayerRunnable shiftRightAction, List<String> lore) {
    ItemStack item = new ItemStack(stack.getType(), stack.getAmount());
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    meta.setLore(newLore);
    item.setItemMeta(meta);
    this.clickAction.put(item, action);
    this.rightClickAction.put(item, rightAction);
    this.shiftAction.put(item, shiftAction);
    this.shiftRightAction.put(item, shiftRightAction);
    this.gui.setItem(slot, item);
  }
  
  public void createGuiItemUsingName(ItemStack item, int slot, String name, PlayerRunnable action, PlayerRunnable rightAction, List<String> lore) {
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    if (newLore.size() > 0)
      meta.setLore(newLore); 
    item.setItemMeta(meta);
    this.gui.setItem(slot, item);
    this.nameMap.put(item.getItemMeta().getDisplayName(), action);
    this.nameMap2.put(item.getItemMeta().getDisplayName(), rightAction);
  }
  
  public void createGuiItem(ItemStack item, int slot, String name, PlayerRunnable action, String... lore) {
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    if (newLore.size() > 0)
      meta.setLore(newLore); 
    item.setItemMeta(meta);
    this.gui.setItem(slot, item);
    this.clickAction.put(item, action);
  }
  
  public void createGuiItemUsingName(ItemStack item, int slot, String name, PlayerRunnable action, String... lore) {
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    if (newLore.size() > 0)
      meta.setLore(newLore); 
    item.setItemMeta(meta);
    this.gui.setItem(slot, item);
    this.nameMap.put(item.getItemMeta().getDisplayName(), action);
  }
  
  public void createGuiItem(Material material, int amount, Byte id, int slot, String name, PlayerRunnable action, String... lore) {
    ItemStack item = new ItemStack(material, amount, (short)id.byteValue());
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Color.code(name));
    List<String> newLore = new ArrayList<>();
    for (String s : lore)
      newLore.add(Color.code(s)); 
    meta.setLore(newLore);
    item.setItemMeta(meta);
    this.clickAction.put(item, action);
    this.gui.setItem(slot, item);
  }
  
  public void createGuiItem(ItemStack item, int slot) {
    this.gui.setItem(slot, item);
  }
  
  public void setBackGroundColor(int color) {
    ItemStack glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)color);
    ItemMeta glassMeta = glass.getItemMeta();
    glassMeta.setDisplayName(Color.code("&b"));
    glass.setItemMeta(glassMeta);
    for (int i = 0; i < this.size; i++) {
      if (this.gui.getItem(i) == null)
        this.gui.setItem(i, glass); 
      if (this.gui.getItem(i) != null && 
        this.gui.getItem(i).getType() == Material.AIR)
        this.gui.setItem(i, glass); 
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\gui\Gui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */