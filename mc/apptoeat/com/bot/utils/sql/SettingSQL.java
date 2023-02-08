package mc.apptoeat.com.bot.utils.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mc.apptoeat.com.bot.main;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;

public class SettingSQL {
  Map<String, Map<String, Object>> values = new HashMap<>();
  
  Map<String, Map<String, Double>> randomizers = new HashMap<>();
  
  private final String defaultPath;
  
  public SettingSQL(String defaultPath) {
    this.defaultPath = defaultPath;
    load();
  }
  
  public Object getOrDefaultValue(String setting, String object, Object defaultValue, boolean randomizer) {
    String path = setting + "_" + object + "_" + randomizer;
    SQLData data = sqlInfo().getOrDefault(this.defaultPath, path, getDefaultSQLData(this.defaultPath, path, defaultValue));
    updateValueRunTime(setting, object, data.getValue());
    return data.getValue();
  }
  
  public void updateValue(String setting, String object, Object updated, boolean randomizer) {
    if (Thread.currentThread() == (MinecraftServer.getServer()).primaryThread)
      Bukkit.broadcastMessage("update value run on the main thread... not good"); 
    String path = setting + "_" + object + "_" + randomizer;
    sqlInfo().updateData(getDefaultSQLData(this.defaultPath, path, updated));
    updateValueRunTime(setting, object, updated);
  }
  
  public void updateValueRunTime(String setting, String object, Object updated) {
    Map<String, Object> objects = this.values.getOrDefault(setting, new HashMap<>());
    objects.put(object, updated);
    this.values.put(setting, objects);
  }
  
  public double getOrDefaultRandomizer(String setting, String object, Double defaultRandom) {
    Map<String, Double> objects = this.randomizers.getOrDefault(setting, new HashMap<>());
    return ((Double)objects.getOrDefault(object, defaultRandom)).doubleValue();
  }
  
  public void updateRandomizer(String setting, String object, double updated) {
    Map<String, Double> objects = this.randomizers.getOrDefault(setting, new HashMap<>());
    objects.put(object, Double.valueOf(updated));
    this.randomizers.put(setting, objects);
  }
  
  public void load() {}
  
  public void update() {}
  
  public Map<String, Map<String, Object>> readSQLData(List<SQLData> sqlData) {
    Map<String, Map<String, Object>> result = new HashMap<>();
    sqlData.forEach(data -> {
          String[] p = data.getPath().split("_");
          String setting = p[0];
          String sb = p[1];
          Object value = null;
          if (data.getDoubleValue() != -1.0D)
            value = Double.valueOf(data.getDoubleValue()); 
          if (value == null && data.getIntValue() != -1)
            value = Integer.valueOf(data.intValue); 
          if (value == null)
            value = Boolean.valueOf(data.booleanValue); 
          Map<String, Object> current = (Map<String, Object>)result.getOrDefault(setting, new HashMap<>());
          current.put(sb, value);
          result.put(setting, current);
        });
    return result;
  }
  
  public List<SQLData> convertToSQLData(String defaultPath) {
    List<SQLData> result = new ArrayList<>();
    this.values.forEach((setting, sbMap) -> sbMap.forEach(()));
    return result;
  }
  
  private SQLData getDefaultSQLData(String defaultPath, String path, Object o) {
    double dv = -1.0D;
    boolean bv = false;
    int iv = -1;
    if (o instanceof Double) {
      dv = ((Double)o).doubleValue();
    } else if (o instanceof Integer) {
      iv = ((Integer)o).intValue();
    } else if (o instanceof Boolean) {
      bv = ((Boolean)o).booleanValue();
    } 
    return new SQLData(defaultPath, path, dv, bv, iv);
  }
  
  private SQLInfo sqlInfo() {
    return main.getInstance().getSqlInfo();
  }
  
  public static class SQLData {
    private String defaultPath;
    
    private String path;
    
    private double doubleValue;
    
    private boolean booleanValue;
    
    private int intValue;
    
    public SQLData(String defaultPath, String path, double doubleValue, boolean booleanValue, int intValue) {
      this.defaultPath = defaultPath;
      this.path = path;
      this.doubleValue = doubleValue;
      this.booleanValue = booleanValue;
      this.intValue = intValue;
    }
    
    public void setDefaultPath(String defaultPath) {
      this.defaultPath = defaultPath;
    }
    
    public void setPath(String path) {
      this.path = path;
    }
    
    public void setDoubleValue(double doubleValue) {
      this.doubleValue = doubleValue;
    }
    
    public void setBooleanValue(boolean booleanValue) {
      this.booleanValue = booleanValue;
    }
    
    public void setIntValue(int intValue) {
      this.intValue = intValue;
    }
    
    public String getDefaultPath() {
      return this.defaultPath;
    }
    
    public String getPath() {
      return this.path;
    }
    
    public double getDoubleValue() {
      return this.doubleValue;
    }
    
    public boolean isBooleanValue() {
      return this.booleanValue;
    }
    
    public int getIntValue() {
      return this.intValue;
    }
    
    public Object getValue() {
      Object value = null;
      if (this.doubleValue != -1.0D)
        value = Double.valueOf(this.doubleValue); 
      if (value == null && this.intValue != -1)
        value = Integer.valueOf(this.intValue); 
      if (value == null)
        value = Boolean.valueOf(this.booleanValue); 
      return value;
    }
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\sql\SettingSQL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */