package mc.apptoeat.com.bot.utils;

import mc.apptoeat.com.bot.utils.objects.AABB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.util.Vector;

public class BlockManager {
  public static Material getBlockType(Location location) {
    Block block = location.getBlock();
    Block belowBlock = location.getBlock();
    Material material = block.getType();
    AABB hitBox = blockHitBox(block);
    AABB belowBlockHitBox = blockHitBox(belowBlock);
    if (!hitBox.isColliding(location.toVector()))
      return Material.AIR; 
    if (belowBlockHitBox.isColliding(location.toVector()))
      return belowBlock.getType(); 
    return material;
  }
  
  public static AABB blockHitBox(Block block) {
    IBlockData blockData = ((CraftWorld)block.getWorld()).getHandle().getType(new BlockPosition(block.getX(), block.getY(), block.getZ()));
    Block blockNative = blockData.getBlock();
    blockNative.updateShape((IBlockAccess)((CraftWorld)block.getWorld()).getHandle(), new BlockPosition(block.getX(), block.getY(), block.getZ()));
    Vector min = new Vector(block.getX() + blockNative.B(), block.getY() + blockNative.D(), block.getZ() + blockNative.F());
    Vector max = new Vector(block.getX() + blockNative.C(), block.getY() + blockNative.E(), block.getZ() + blockNative.G());
    return new AABB(min, max);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\BlockManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */