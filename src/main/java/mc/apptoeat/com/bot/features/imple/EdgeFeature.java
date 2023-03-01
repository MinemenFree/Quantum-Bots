package mc.apptoeat.com.bot.features.imple;

import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.features.Feature;
import mc.apptoeat.com.bot.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class EdgeFeature extends Feature {
  public void tickEvent(Bot bot) {
    if (!bot.getMovements().isEdgeDetector())
      return; 
    Location steppedBlock = bot.getMovements().getLocation().clone().subtract(new Vector(0.0D, 0.95D, 0.0D));
    boolean found = false;
    for (Block block : MathUtils.blocksFromTwoPoints(steppedBlock.clone().add(new Vector(0.3D, 0.0D, 0.3D)), steppedBlock.clone().add(new Vector(0.3D, 0.0D, 0.3D)))) {
      if (block.getType().equals(Material.AIR)) {
        found = true;
        Bukkit.broadcastMessage("stop movement");
      } 
    } 
    if (bot.getMovements().isEdge() && !found)
      bot.getMovements().setMove(true); 
    if (found)
      bot.getMovements().setMove(false); 
    bot.getMovements().setEdge(found);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\imple\EdgeFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */