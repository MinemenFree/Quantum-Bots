package mc.apptoeat.com.bot.utils.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mc.apptoeat.com.bot.main;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SQLInfo {
  private final SQLManager manager;
  
  public SQLInfo(SQLManager sql) {
    this.manager = sql;
    createTable();
    createTableNames();
  }
  
  public void createTable() {
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS settings (pathdefault VARCHAR(100),path VARCHAR(100),doubleval double,booleanval boolean,intval int,PRIMARY KEY (pathdefault, path))");
      ps.executeUpdate();
    } catch (SQLException exception) {
      exception.printStackTrace();
    } 
  }
  
  public void createTableNames() {
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS names (pathdefault VARCHAR(100),path VARCHAR(100),name VARCHAR(100))");
      ps.executeUpdate();
    } catch (SQLException exception) {
      exception.printStackTrace();
    } 
  }
  
  public String getOrDefaultNames(String defaultPath, String path, String defaults) {
    if (existsNames(defaultPath, path))
      try {
        String name;
        PreparedStatement ps = this.manager.getConnection().prepareStatement("SELECT * FROM names WHERE pathdefault=? AND path=?");
        ps.setString(1, defaultPath);
        ps.setString(2, path);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
          name = resultSet.getString("name");
        } else {
          Bukkit.broadcastMessage("SQL ERROR, Tried to find data but was missing defaultPath " + defaultPath + " path " + path);
          return defaults;
        } 
        return name;
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    updateDataNames(defaultPath, path, defaultPath);
    return defaults;
  }
  
  public void updateDataNames(final String defaultPath, final String path, final String name) {
    (new BukkitRunnable() {
        public void run() {
          if (SQLInfo.this.existsNames(defaultPath, path)) {
            SQLInfo.this.updateDataWithOutCheckNames(defaultPath, path, name);
            return;
          } 
          SQLInfo.this.createDataNames(defaultPath, path, name);
        }
      }).runTaskAsynchronously((Plugin)main.getInstance());
  }
  
  public SettingSQL.SQLData getOrDefault(String defaultPath, String path, SettingSQL.SQLData defaultData) {
    if (exists(defaultPath, path))
      try {
        PreparedStatement ps = this.manager.getConnection().prepareStatement("SELECT * FROM settings WHERE pathdefault=? AND path=?");
        ps.setString(1, defaultPath);
        ps.setString(2, path);
        ResultSet resultSet = ps.executeQuery();
        double doubleVal = -1.0D;
        int intVal = -1;
        boolean booleanVal = false;
        if (resultSet.next()) {
          doubleVal = resultSet.getDouble("doubleval");
          booleanVal = resultSet.getBoolean("booleanval");
          intVal = resultSet.getInt("intval");
        } else {
          return defaultData;
        } 
        return new SettingSQL.SQLData(defaultPath, path, doubleVal, booleanVal, intVal);
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    updateData(defaultData);
    return defaultData;
  }
  
  public List<String> deletePreset(String defaultPath) {
    List<String> result = new ArrayList<>();
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("DELETE FROM settings WHERE pathdefault=?");
      ps.setString(1, defaultPath);
      ps.executeUpdate();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return result;
  }
  
  public List<String> getPathsFromPathDefault(String defaultPath) {
    List<String> result = new ArrayList<>();
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("SELECT * FROM settings");
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()) {
        String value = resultSet.getString("pathdefault");
        if (value != null && !result.contains(value) && value.contains(defaultPath))
          result.add(value); 
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return result;
  }
  
  public void updateData(final SettingSQL.SQLData sqlData) {
    (new BukkitRunnable() {
        public void run() {
          if (SQLInfo.this.exists(sqlData.getDefaultPath(), sqlData.getPath())) {
            SQLInfo.this.updateDataWithOutCheck(sqlData);
            return;
          } 
          SQLInfo.this.createData(sqlData);
        }
      }).runTaskAsynchronously((Plugin)main.getInstance());
  }
  
  public void updateDataWithOutCheck(SettingSQL.SQLData sqlData) {
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("UPDATE settings SET doubleval=?, booleanval=?, intval=? WHERE pathdefault=? AND path=?");
      ps.setDouble(1, sqlData.getDoubleValue());
      ps.setBoolean(2, sqlData.isBooleanValue());
      ps.setInt(3, sqlData.getIntValue());
      ps.setString(4, sqlData.getDefaultPath());
      ps.setString(5, sqlData.getPath());
      ps.executeUpdate();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void updateDataWithOutCheckNames(String defaultPath, String path, String name) {
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("UPDATE names SET name=? WHERE pathdefault=? AND path=?");
      ps.setString(1, name);
      ps.setString(2, defaultPath);
      ps.setString(3, path);
      ps.executeUpdate();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void createData(SettingSQL.SQLData sqlData) {
    try {
      if (!exists(sqlData.getDefaultPath(), sqlData.getPath())) {
        PreparedStatement ps = this.manager.getConnection().prepareStatement("INSERT IGNORE INTO settings (pathdefault,path,doubleval,booleanval,intval) VALUES (?,?,?,?,?)");
        ps.setString(1, sqlData.getDefaultPath());
        ps.setString(2, sqlData.getPath());
        ps.setDouble(3, sqlData.getDoubleValue());
        ps.setBoolean(4, sqlData.isBooleanValue());
        ps.setInt(5, sqlData.getIntValue());
        ps.executeUpdate();
      } 
    } catch (SQLException exception) {
      exception.printStackTrace();
    } 
  }
  
  public void createDataNames(String defaultPath, String path, String name) {
    try {
      if (!existsNames(defaultPath, path)) {
        PreparedStatement ps = this.manager.getConnection().prepareStatement("INSERT IGNORE INTO names (pathdefault,path,name) VALUES (?,?,?)");
        ps.setString(1, defaultPath);
        ps.setString(2, path);
        ps.setString(3, name);
        ps.executeUpdate();
      } 
    } catch (SQLException exception) {
      exception.printStackTrace();
    } 
  }
  
  public boolean exists(String defaultPath, String path) {
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("SELECT * FROM settings WHERE pathdefault=? AND path=?");
      ps.setString(1, defaultPath);
      ps.setString(2, path);
      ResultSet resultSet = ps.executeQuery();
      if (resultSet.next())
        return true; 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return false;
  }
  
  public boolean existsNames(String defaultPath, String path) {
    try {
      PreparedStatement ps = this.manager.getConnection().prepareStatement("SELECT * FROM names WHERE pathdefault=? AND path=?");
      ps.setString(1, defaultPath);
      ps.setString(2, path);
      ResultSet resultSet = ps.executeQuery();
      if (resultSet.next())
        return true; 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return false;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\sql\SQLInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */