package mc.apptoeat.com.bot.features;

import java.util.ArrayList;
import java.util.List;
import mc.apptoeat.com.bot.features.imple.ArmorEquipFeature;
import mc.apptoeat.com.bot.features.imple.EatingAndDrinking;
import mc.apptoeat.com.bot.features.imple.EdgeFeature;
import mc.apptoeat.com.bot.features.imple.HealthPotionThrowing;
import mc.apptoeat.com.bot.features.imple.Pearling;
import mc.apptoeat.com.bot.features.imple.PreGap;
import mc.apptoeat.com.bot.features.imple.Soups;
import mc.apptoeat.com.bot.features.imple.StrafeFeature;
import mc.apptoeat.com.bot.features.imple.WTapFeature;

public class FeatureManager {
  final List<Feature> features;
  
  public List<Feature> getFeatures() {
    return this.features;
  }
  
  public FeatureManager() {
    this.features = new ArrayList<>();
    addFeature((Feature)new EatingAndDrinking());
    addFeature((Feature)new HealthPotionThrowing());
    addFeature((Feature)new ArmorEquipFeature());
    addFeature((Feature)new Pearling());
    addFeature((Feature)new StrafeFeature());
    addFeature((Feature)new Soups());
    addFeature((Feature)new WTapFeature());
    addFeature((Feature)new PreGap());
    addFeature((Feature)new EdgeFeature());
  }
  
  public void addFeature(Feature feature) {
    this.features.add(feature);
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\features\FeatureManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */