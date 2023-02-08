package mc.apptoeat.com.bot.support.papi;

import mc.apptoeat.com.bot.support.PluginSupport;

public class PlaceHolderApiSupport extends PluginSupport {
  public PlaceHolderApiSupport(String name) {
    super(name);
  }
  
  public PluginSupport enable() {
    (new BotPingPlaceHolder()).register();
    (new HealthPlaceHolder()).register();
    (new BotStatsPlaceHolders()).register();
    return super.enable();
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\support\papi\PlaceHolderApiSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */