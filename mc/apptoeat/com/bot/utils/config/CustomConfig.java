package mc.apptoeat.com.bot.utils.config;

import java.io.File;
import java.io.IOException;
import mc.apptoeat.com.bot.main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig {
  private final FileConfiguration config;
  
  private final File file;
  
  public FileConfiguration getConfig() {
    return this.config;
  }
  
  public File getFile() {
    return this.file;
  }
  
  public CustomConfig(String name) {
    this.file = new File(main.getInstance().getDataFolder(), name + ".yml");
    if (!this.file.exists())
      try {
        this.file.createNewFile();
      } catch (IOException iOException) {} 
    this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    saveConfig();
  }
  
  public void saveConfig() {
    try {
      this.config.save(this.file);
    } catch (IOException iOException) {}
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\config\CustomConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */