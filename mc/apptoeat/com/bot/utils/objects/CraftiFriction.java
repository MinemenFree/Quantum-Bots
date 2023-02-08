package mc.apptoeat.com.bot.utils.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;

public enum CraftiFriction {
  DEFAULT(0.6F),
  ICE(0.98F),
  PACKED_ICE(0.98F),
  SLIME_BLOCK(0.8F);
  
  private final float factor;
  
  CraftiFriction(float factor) {
    this.factor = factor;
  }
  
  public float getFactor() {
    return this.factor;
  }
  
  public static float getFactor(Block block) {
    Material type = block.getType();
    try {
      return valueOf(type.name()).getFactor();
    } catch (Exception exception) {
      return DEFAULT.getFactor();
    } 
  }
  
  public static float getFactor(Material type) {
    try {
      return valueOf(type.name()).getFactor();
    } catch (Exception exception) {
      return DEFAULT.getFactor();
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\objects\CraftiFriction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */