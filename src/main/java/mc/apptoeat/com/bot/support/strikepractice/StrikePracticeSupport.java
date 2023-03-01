package mc.apptoeat.com.bot.support.strikepractice;

import ga.strikepractice.StrikePractice;
import mc.apptoeat.com.bot.support.PluginSupport;

public class StrikePracticeSupport extends PluginSupport {
  private SPEvents events;
  
  private SPManager manager;
  
  public void setEvents(SPEvents events) {
    this.events = events;
  }
  
  public void setManager(SPManager manager) {
    this.manager = manager;
  }
  
  public StrikePracticeSupport(String name) {
    super(name);
  }
  
  public SPEvents getEvents() {
    return this.events;
  }
  
  public SPManager getManager() {
    return this.manager;
  }
  
  public PluginSupport enable() {
    this.api = StrikePractice.getAPI();
    this.manager = new SPManager(this);
    this.events = new SPEvents(this);
    return super.enable();
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\strikepractice\StrikePracticeSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */