package mc.apptoeat.com.bot.support.papi;

import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.setting.Setting;
import mc.apptoeat.com.bot.bots.setting.SettingObject;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.MathUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BotPingPlaceHolder extends PlaceholderExpansion {
  @NotNull
  public String getIdentifier() {
    return "bot";
  }
  
  @NotNull
  public String getAuthor() {
    return "crafticat";
  }
  
  @NotNull
  public String getVersion() {
    return "1.0";
  }
  
  public boolean canRegister() {
    return true;
  }
  
  public boolean persist() {
    return true;
  }
  
  @Nullable
  public String onPlaceholderRequest(Player player, @NotNull String params) {
    if (player == null)
      return ""; 
    Bot bot = main.getInstance().getBotManager().getNpcFromTarget(player);
    if (bot == null)
      return ""; 
    for (Setting setting : bot.getSettingManager().getSettings()) {
      for (SettingObject sb : setting.getSettings()) {
        if (params.equals(sb.getName()))
          return getValue(sb); 
      } 
    } 
    return "invalid type";
  }
  
  public String getValue(SettingObject sb) {
    if (sb.getObject() instanceof Boolean)
      return sb.isBooleanValue() + ""; 
    if (sb.getObject() instanceof Double)
      return MathUtils.stringOfDouble(Double.valueOf(sb.getDoubleValue()), 3); 
    if (sb.getObject() instanceof Integer)
      return sb.getIntValue() + ""; 
    return null;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\papi\BotPingPlaceHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */