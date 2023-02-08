package mc.apptoeat.com.bot.utils.config;

public class ConfigManager {
  public static CustomConfig data;
  
  public static CustomConfig settings;
  
  public ConfigManager() {
    data = new CustomConfig("data");
    settings = new CustomConfig("settings");
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\config\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */