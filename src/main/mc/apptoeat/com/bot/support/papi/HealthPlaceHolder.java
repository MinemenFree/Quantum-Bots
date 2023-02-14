package mc.apptoeat.com.bot.support.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HealthPlaceHolder extends PlaceholderExpansion {
  @NotNull
  public String getIdentifier() {
    return "bothealth";
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
    return player.getHealth() + "";
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\papi\HealthPlaceHolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */