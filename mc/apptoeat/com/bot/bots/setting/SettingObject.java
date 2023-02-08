package mc.apptoeat.com.bot.bots.setting;

import java.util.ArrayList;
import java.util.List;
import mc.apptoeat.com.bot.utils.MathUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SettingObject {
  private String name;
  
  private ItemStack item;
  
  private Object object;
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setItem(ItemStack item) {
    this.item = item;
  }
  
  public void setObject(Object object) {
    this.object = object;
  }
  
  public void setUpdateBy(double updateBy) {
    this.updateBy = updateBy;
  }
  
  public void setRandomizer(double randomizer) {
    this.randomizer = randomizer;
  }
  
  public void setUpdate(Runnable update) {
    this.update = update;
  }
  
  public String getName() {
    return this.name;
  }
  
  public ItemStack getItem() {
    return this.item;
  }
  
  public Object getObject() {
    return this.object;
  }
  
  private double updateBy = 1.0D;
  
  public double getUpdateBy() {
    return this.updateBy;
  }
  
  private double randomizer = 0.0D;
  
  private Runnable update;
  
  public double getRandomizer() {
    return this.randomizer;
  }
  
  public Runnable getUpdate() {
    return this.update;
  }
  
  public void setObjectUpdate(Object object) {
    this.object = object;
    if (this.update != null)
      this.update.run(); 
  }
  
  public void setRandomizerUpdate(double randomizer) {
    this.randomizer = randomizer;
    if (this.update != null)
      this.update.run(); 
  }
  
  public List<String> getDescription() {
    List<String> result = new ArrayList<>();
    if (this.object instanceof Boolean) {
      result.add("&4&m--*----------*--");
      result.add("&c&lInfo:");
      result.add("&c ● &fStatus: &c" + this.object);
      result.add("");
      result.add("&aClick to change!");
      result.add("&4&m--*----------*--");
    } else {
      result.add("&4&m--*------------------------*--");
      result.add("&c&lSettings:");
      if (this.object instanceof Double)
        result.add("&c ● &f" + this.name + ": &c" + MathUtils.stringOfDouble(Double.valueOf(((Double)this.object).doubleValue()), 3)); 
      if (this.object instanceof Integer)
        result.add("&c ● &f" + this.name + ": &c" + this.object); 
      if (this.object instanceof Double)
        result.add("&c ● &fRandomizer: &c" + MathUtils.stringOfDouble(Double.valueOf(this.randomizer), 3) + " &f(&c" + MathUtils.stringOfDouble(Double.valueOf(((Double)this.object).doubleValue() - this.randomizer), 3) + " &f<-> &c" + MathUtils.stringOfDouble(Double.valueOf(((Double)this.object).doubleValue() + this.randomizer), 3) + "&f)"); 
      if (this.object instanceof Integer)
        result.add("&c ● &fRandomizer: &c" + this.randomizer + " &f(&c" + (((Integer)this.object).intValue() - this.randomizer) + " &f<-> &c" + (((Integer)this.object).intValue() + this.randomizer) + "&f)"); 
      result.add("");
      result.add("&c&lMain:");
      result.add(" &a[+] &fLeft Click");
      result.add(" &c[-] &fRight Click");
      result.add("");
      result.add("&c&lRandomization:");
      result.add(" &a[+] &fShift Left Click");
      result.add(" &c[-] &fShift Right Click");
      result.add("&4&m--*------------------------*--");
    } 
    return result;
  }
  
  public double getDoubleValue() {
    if (this.object instanceof Integer)
      return ((Integer)this.object).intValue(); 
    return ((Double)this.object).doubleValue() + MathUtils.getRandom(-this.randomizer, this.randomizer);
  }
  
  public double getOriginalDouble() {
    if (this.object instanceof Integer)
      return ((Integer)this.object).intValue(); 
    return ((Double)this.object).doubleValue();
  }
  
  public double getRandomizerInt() {
    return (int)this.randomizer;
  }
  
  public int getIntValue() {
    return ((Integer)this.object).intValue() + (int)Math.round(MathUtils.getRandom(-this.randomizer, this.randomizer));
  }
  
  public int getOriginalIntValue() {
    return ((Integer)this.object).intValue();
  }
  
  public boolean isBooleanValue() {
    return ((Boolean)this.object).booleanValue();
  }
  
  public SettingObject(String name, ItemStack item, Object object) {
    this.name = name;
    this.item = item;
    this.object = object;
  }
  
  public SettingObject(String name, ItemStack item, double doubleValue, double updateBy) {
    this.name = name;
    this.item = item;
    this.object = Double.valueOf(doubleValue);
    this.updateBy = updateBy;
  }
  
  public SettingObject(String name, ItemStack item, int intValue, double updateBy) {
    this.name = name;
    this.item = item;
    this.object = Integer.valueOf(intValue);
    this.updateBy = updateBy;
  }
  
  public SettingObject(String name, ItemStack item, boolean booleanValue) {
    this.name = name;
    this.item = item;
    this.object = Boolean.valueOf(booleanValue);
  }
  
  public SettingObject(String name, Material material, boolean booleanValue) {
    this(name, new ItemStack(material, 1), booleanValue);
  }
  
  public SettingObject(String name, Material material, int intValue) {
    this(name, new ItemStack(material, 1), Integer.valueOf(intValue));
  }
  
  public SettingObject(String name, Material material, double doubleValue) {
    this(name, new ItemStack(material, 1), Double.valueOf(doubleValue));
  }
  
  public SettingObject(String name, Material material, int intValue, double updateBy) {
    this(name, new ItemStack(material, 1), intValue, updateBy);
  }
  
  public SettingObject(String name, Material material, double doubleValue, double updateBy) {
    this(name, new ItemStack(material, 1), doubleValue, updateBy);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\setting\SettingObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */