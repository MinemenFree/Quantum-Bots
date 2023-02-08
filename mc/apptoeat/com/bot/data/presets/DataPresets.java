package mc.apptoeat.com.bot.data.presets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mc.apptoeat.com.bot.bots.setting.SettingManager;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.gui.GlobalGui;
import mc.apptoeat.com.bot.utils.gui.imple.SettingGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DataPresets {
  private final List<SettingGui> presets;
  
  private final GlobalGui gui;
  
  private final String name;
  
  public List<SettingGui> getPresets() {
    return this.presets;
  }
  
  public GlobalGui getGui() {
    return this.gui;
  }
  
  public String getName() {
    return this.name;
  }
  
  private final int theme = 15;
  
  public int getTheme() {
    getClass();
    return 15;
  }
  
  private final int maxSize = 27;
  
  public int getMaxSize() {
    getClass();
    return 27;
  }
  
  public DataPresets(String name) {
    this.presets = new ArrayList<>();
    this.gui = new GlobalGui(name, 27);
    this.name = name;
    loadPresets();
    loadGui();
  }
  
  public void loadPresets() {
    String path = "presets." + this.name;
    Bukkit.broadcastMessage("SQL: START");
    System.out.println("SQL: START");
    if (!main.getInstance().getSqlManager().isEnabled()) {
      ConfigurationSection section = main.getInstance().getConfig().getConfigurationSection(path);
      if (section == null)
        return; 
      section.getKeys(false).forEach(p -> {
            String p2 = "." + p;
            loadPreset(SettingManager.loadSettings(path + p2, (JavaPlugin)main.getInstance(), path + p2, p));
          });
    } else {
      Bukkit.broadcastMessage("DEBUG:START");
      System.out.println("DEBUG:START");
      List<String> section = main.getInstance().getSqlInfo().getPathsFromPathDefault(path);
      if (section == null)
        return; 
      section.forEach(p -> {
            String p2 = p.replace(path, "").replace(".", "");
            Bukkit.broadcastMessage("DEBUG:P2 " + p2);
            System.out.println("DEBUG:P2 " + p2);
            loadPreset(SettingManager.loadSettings(p, (JavaPlugin)main.getInstance(), p, p2));
          });
    } 
  }
  
  public void loadGui() {
    this.gui.setGui(() -> {
          this.gui.reset();
          int slot = 0;
          for (SettingGui preset : this.presets) {
            SettingManager manager = preset.getManager();
            List<String> lore = new ArrayList<>();
            lore.add("&4&m--*------------------------*--");
            lore.add("&c&lPreset:");
            lore.add("");
            lore.add("&c&lMain:");
            lore.add(" &a[+] &fLeft Click &a(To open)");
            lore.add(" &c[-] &fRight Click &c(To delete)");
            lore.add("&4&m--*------------------------*--");
            this.gui.createGuiItemUsingName(manager.getSkullItem("crafticat", true), slot, "&b&l" + manager.getBotName().replace("%player%", "crafticat"), (), (), lore);
            if (slot++ >= 26)
              break; 
          } 
          this.gui.createGuiItem(new ItemStack(Material.STONE), 26, "&a&lCreate new Preset", (), new String[0]);
          this.gui.setBackGroundColor(15);
        });
  }
  
  public SettingGui getPreset() {
    if (this.presets.isEmpty())
      return null; 
    Random random = new Random();
    int index = random.nextInt(this.presets.size());
    return this.presets.get(index);
  }
  
  public SettingGui createPreset() {
    String presetName = "default";
    int counter = 0;
    List<String> names = presetsNames();
    while (names.contains(presetName)) {
      counter++;
      presetName = presetName + counter;
    } 
    String path = "presets." + this.name + "." + presetName;
    Bukkit.broadcastMessage("path " + path);
    SettingGui preset = new SettingGui(SettingManager.loadSettings(path, (JavaPlugin)main.getInstance(), path), null);
    preset.getManager().setName(presetName);
    return addPreset(preset);
  }
  
  public SettingGui loadPreset(SettingManager manager) {
    SettingGui preset = new SettingGui(manager, null);
    return addPreset(preset);
  }
  
  public boolean findPreset(String name) {
    for (SettingGui preset : this.presets) {
      if (preset.getManager().getName().equals(name))
        return true; 
    } 
    return false;
  }
  
  public List<String> presetsNames() {
    List<String> result = new ArrayList<>();
    for (SettingGui preset : this.presets)
      result.add(preset.getManager().getName()); 
    return result;
  }
  
  public SettingGui addPreset(SettingGui preset) {
    this.presets.add(preset);
    return preset;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\data\presets\DataPresets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */