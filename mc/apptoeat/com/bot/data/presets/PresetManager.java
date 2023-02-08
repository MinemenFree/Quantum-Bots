package mc.apptoeat.com.bot.data.presets;

import ga.strikepractice.StrikePractice;
import ga.strikepractice.battlekit.BattleKit;
import ga.strikepractice.fights.botduel.BotDuel;
import ga.strikepractice.npc.CitizensNPC;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mc.apptoeat.com.bot.utils.Color;
import mc.apptoeat.com.bot.utils.Command;
import org.bukkit.entity.Player;

public class PresetManager {
  public List<DataPresets> getDataList() {
    return this.dataList;
  }
  
  public DataPresets getUnranked() {
    return this.unranked;
  }
  
  public DataPresets getRanked() {
    return this.ranked;
  }
  
  private final List<DataPresets> dataList = new ArrayList<>();
  
  private final DataPresets unranked = registerData("unranked");
  
  private final DataPresets ranked = registerData("ranked");
  
  public DataPresets registerData(String name) {
    DataPresets p = new DataPresets(name);
    createCMD(p, name);
    return p;
  }
  
  public void createCMD(DataPresets data, String name) {
    Command adminCMD = new Command(data.getName() + "admin", "Presets admin gui", data.getName() + "admin", null);
    adminCMD.setOnCommand(player -> {
          if (!player.hasPermission("bot.staff")) {
            player.sendMessage(Color.code("&cYou do not have permission to execute this command."));
            return;
          } 
          data.getGui().minOpen(player);
        });
    Command command = new Command(data.getName(), "queue", data.getName(), null);
    command.setOnCommand(player -> {
          String kit = command.getArgs()[0];
          BotDuel duel = new BotDuel(StrikePractice.getInstance(), player.getName(), BattleKit.getKit(kit));
          if (name.equals("ranked"))
            duel.setDifficulty(CitizensNPC.Difficulty.RANKED); 
          if (name.equals("unranked"))
            duel.setDifficulty(CitizensNPC.Difficulty.UNRANKED); 
          duel.setKit(BattleKit.getKit(kit));
          if (duel.canStart())
            duel.start(); 
        });
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\data\presets\PresetManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */