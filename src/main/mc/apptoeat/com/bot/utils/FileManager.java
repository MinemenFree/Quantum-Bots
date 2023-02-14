package mc.apptoeat.com.bot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mc.apptoeat.com.bot.utils.config.CustomConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class FileManager {
  public static void arraysSetVal(JavaPlugin main, ArrayList list, String path1, String path2, Object value, Boolean defaults) {
    list.forEach(item -> {
          if (defaults.booleanValue()) {
            setDefault(main, path1 + "." + item + "." + path2, value);
          } else {
            main.getConfig().set(path1 + "." + item + "." + path2, value);
          } 
        });
  }
  
  public static void setDefault(JavaPlugin main, String path, Object value) {
    if (!main.getConfig().isSet(path)) {
      main.getConfig().set(path, value);
      main.saveConfig();
    } 
  }
  
  public static double getOrDefault(JavaPlugin main, String path, Double def) {
    if (!main.getConfig().isSet(path)) {
      main.getConfig().set(path, def);
      main.saveConfig();
      return def.doubleValue();
    } 
    return main.getConfig().getDouble(path);
  }
  
  public static <V> V getOrDefault(CustomConfig config, String path, V def) {
    if (!config.getConfig().isSet(path)) {
      config.getConfig().set(path, def);
      config.saveConfig();
      return def;
    } 
    return (V)config.getConfig().get(path);
  }
  
  public static void update(CustomConfig config, String path, Object value) {
    config.getConfig().set(path, value);
    config.saveConfig();
  }
  
  public static boolean getOrDefault(JavaPlugin main, String path, boolean def) {
    if (!main.getConfig().isSet(path)) {
      main.getConfig().set(path, Boolean.valueOf(def));
      main.saveConfig();
      return def;
    } 
    return main.getConfig().getBoolean(path);
  }
  
  public static Object getOrDefault(JavaPlugin main, String path, Object def) {
    if (!main.getConfig().isSet(path)) {
      main.getConfig().set(path, def);
      main.saveConfig();
      return def;
    } 
    return main.getConfig().get(path);
  }
  
  public static <V> V getOrDefaultSimple(JavaPlugin main, String path, V def) {
    if (!main.getConfig().isSet(path)) {
      main.getConfig().set(path, def);
      main.saveConfig();
      return def;
    } 
    return (V)main.getConfig().get(path);
  }
  
  public static List<Map<String, Object>> getOrDefaultSection(JavaPlugin main, String path, Object def) {
    return null;
  }
  
  public static List<?> getOrDefault(JavaPlugin main, String path, ArrayList<?> def) {
    if (!main.getConfig().isSet(path)) {
      main.getConfig().set(path, def);
      main.saveConfig();
      return def;
    } 
    return main.getConfig().getList(path);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\FileManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */