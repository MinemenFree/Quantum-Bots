package mc.apptoeat.com.bot.utils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SQLManager {
  private final boolean enabled = FileManager.getOrDefault((JavaPlugin)main.getInstance(), "sql.enabled", false);
  
  public boolean isEnabled() {
    return this.enabled;
  }
  
  private final String host = (String)FileManager.getOrDefault((JavaPlugin)main.getInstance(), "sql.host", "localhost");
  
  public String getHost() {
    return this.host;
  }
  
  private final String port = (String)FileManager.getOrDefault((JavaPlugin)main.getInstance(), "sql.port", "3306");
  
  public String getPort() {
    return this.port;
  }
  
  private final String database = (String)FileManager.getOrDefault((JavaPlugin)main.getInstance(), "sql.database", "botdata");
  
  public String getDatabase() {
    return this.database;
  }
  
  private final String username = (String)FileManager.getOrDefault((JavaPlugin)main.getInstance(), "sql.username", "root");
  
  public String getUsername() {
    return this.username;
  }
  
  private final String password = (String)FileManager.getOrDefault((JavaPlugin)main.getInstance(), "sql.password", "");
  
  private Connection connection;
  
  public String getPassword() {
    return this.password;
  }
  
  public Connection getConnection() {
    return this.connection;
  }
  
  public boolean isConnected() {
    return (this.connection != null);
  }
  
  public void connect() throws ClassNotFoundException, SQLException {
    if (isConnected())
      return; 
    this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
  }
  
  public void disconnect() {
    if (!isConnected())
      return; 
    try {
      this.connection.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\sql\SQLManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */