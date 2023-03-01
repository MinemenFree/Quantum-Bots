package mc.apptoeat.com.bot.utils;

import java.util.ArrayList;
import mc.apptoeat.com.bot.main;
import mc.apptoeat.com.bot.utils.objects.PlayerRunnable;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

public class Command extends BukkitCommand {
  private PlayerRunnable onCommand;
  
  private String arg;
  
  private String[] args;
  
  private Player player;
  
  public void setOnCommand(PlayerRunnable onCommand) {
    this.onCommand = onCommand;
  }
  
  public void setArg(String arg) {
    this.arg = arg;
  }
  
  public void setArgs(String[] args) {
    this.args = args;
  }
  
  public void setPlayer(Player player) {
    this.player = player;
  }
  
  public PlayerRunnable getOnCommand() {
    return this.onCommand;
  }
  
  public String getArg() {
    return this.arg;
  }
  
  public String[] getArgs() {
    return this.args;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Command(String name, String description, String usageMessage, PlayerRunnable command) {
    super(name);
    this.description = description;
    this.usageMessage = usageMessage;
    setAliases(new ArrayList());
    this.onCommand = command;
    ((CraftServer)main.getInstance().getServer()).getCommandMap().register(name, (org.bukkit.command.Command)this);
  }
  
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    try {
      if (sender instanceof Player) {
        Player player = (Player)sender;
        this.arg = commandLabel;
        this.args = args;
        this.player = player;
        this.onCommand.run(player);
      } 
      return false;
    } catch (Throwable $ex) {
      throw $ex;
    } 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bo\\utils\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */