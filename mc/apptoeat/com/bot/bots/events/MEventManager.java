package mc.apptoeat.com.bot.bots.events;

import java.util.ArrayList;
import mc.apptoeat.com.bot.botevents.BotEvent;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.main;

public class MEventManager {
  private final Bot bot;
  
  private final ArrayList<Manager> managers = new ArrayList<>();
  
  public MEventManager(Bot bot) {
    this.bot = bot;
  }
  
  public void addEvent(Manager manager) {
    this.managers.add(manager);
  }
  
  public void tickPre() {
    try {
      this.managers.forEach(Manager::tickPre);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void tickPost() {
    try {
      this.managers.forEach(Manager::tickPost);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void tickMid() {
    try {
      this.managers.forEach(Manager::tickMid);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  public void callEvents() {
    for (BotEvent events : main.getInstance().getEventManager().getEvents())
      events.callTick(this.bot); 
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\events\MEventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */