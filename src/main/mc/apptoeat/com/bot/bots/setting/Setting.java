package mc.apptoeat.com.bot.bots.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Setting implements ConfigurationSerializable {
  private final ArrayList<SettingObject> settings;
  
  private final Material material;
  
  private final String fancyName;
  
  private final String name;
  
  public void setId(int id) {
    this.id = id;
  }
  
  public void setSlot(int slot) {
    this.slot = slot;
  }
  
  public void setDescription(List<String> description) {
    this.description = description;
  }
  
  public ArrayList<SettingObject> getSettings() {
    return this.settings;
  }
  
  public Material getMaterial() {
    return this.material;
  }
  
  public String getFancyName() {
    return this.fancyName;
  }
  
  public String getName() {
    return this.name;
  }
  
  private int id = 0;
  
  private int slot;
  
  private List<String> description;
  
  public int getId() {
    return this.id;
  }
  
  public int getSlot() {
    return this.slot;
  }
  
  public List<String> getDescription() {
    return this.description;
  }
  
  private static final HashMap<String, Setting> defaults = new HashMap<>();
  
  public SettingObject getSettingByName(String name) {
    for (SettingObject sb : this.settings) {
      if (Objects.equals(sb.getName(), name))
        return sb; 
    } 
    return null;
  }
  
  public Setting(Material material, String fancyName, String name, int slot, String... strings) {
    this(material, fancyName, name, slot, Arrays.asList(strings));
  }
  
  public Setting(Material material, String fancyName, String name, int slot, List<String> strings) {
    this.settings = new ArrayList<>();
    this.material = material;
    this.fancyName = fancyName;
    this.name = name;
    this.slot = slot;
    this.description = strings;
    defaults.put(fancyName, this);
  }
  
  public void addSetting(SettingObject object) {
    this.settings.add(object);
  }
  
  public void addSettings(SettingObject... objects) {
    for (SettingObject object : objects)
      addSetting(object); 
  }
  
  public Setting setMaterialId(int id) {
    this.id = id;
    return this;
  }
  
  public void addSettings(ArrayList<SettingObject> objects) {
    for (SettingObject object : objects)
      addSetting(object); 
  }
  
  public Map<String, Object> serialize() {
    Map<String, Object> m = new HashMap<>();
    this.settings.forEach(setting -> m.put(setting.getName(), setting.getObject()));
    m.put("name", this.fancyName);
    return m;
  }
  
  public Setting clone() {
    Setting setting = new Setting(this.material, this.fancyName, this.name, this.slot, this.description);
    setting.addSettings(this.settings);
    return setting;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */