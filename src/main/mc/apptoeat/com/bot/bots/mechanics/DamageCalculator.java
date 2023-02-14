package mc.apptoeat.com.bot.bots.mechanics;

import com.google.common.base.Function;
import java.util.Random;
import mc.apptoeat.com.bot.bots.Bot;
import mc.apptoeat.com.bot.bots.events.Manager;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class DamageCalculator extends Manager {
  public DamageCalculator(Bot bot) {
    super(bot);
  }
  
  protected void damage(final DamageSource damagesource, float f) {
    float originalDamage = f;
    InventoryManager manager = this.bot.getInventoryManager();
    double d = ((damagesource == DamageSource.ANVIL || damagesource == DamageSource.FALLING_BLOCK) && manager.getHelmet() != null) ? -(f - f * 0.75D) : -0.0D;
    f = (float)(f + d);
    double d2 = (this.bot.getAttackingManager().isBlocking() && f > 0.0D) ? -(f - (1.0D + f) * 0.5D) : -0.0D;
    f = (float)(f + d2);
    double d3 = -(f - applyArmor(damagesource, f, manager));
    f = (float)(f + d3);
    Function<Double, Double> resistance = new Function<Double, Double>() {
        public Double apply(Double f) {
          if (DamageCalculator.this.bot.getEffectsManager().getLevel(PotionEffectType.DAMAGE_RESISTANCE) != 0 && damagesource != DamageSource.OUT_OF_WORLD) {
            int i = (DamageCalculator.this.bot.getEffectsManager().getLevel(PotionEffectType.DAMAGE_RESISTANCE) + 1) * 5;
            int j = 25 - i;
            float f1 = f.floatValue() * j;
            return Double.valueOf(-(f.doubleValue() - (f1 / 25.0F)));
          } 
          return Double.valueOf(-0.0D);
        }
      };
    float resistanceModifier = ((Double)resistance.apply(Double.valueOf(f))).floatValue();
    f += resistanceModifier;
    double d4 = -(f - applyEnchants(damagesource, f, manager));
    f = (float)(f + d4);
    double d5 = -Math.max(f - Math.max(f - this.bot.getHealthManager().getAbsHearts(), 0.0D), 0.0D);
    float absorptionModifier = (float)d5;
    Random random = new Random();
    ItemStack[] equipments = manager.getArmorStacks();
    if ((damagesource == DamageSource.ANVIL || damagesource == DamageSource.FALLING_BLOCK) && manager.getHelmet() != null)
      manager.damageArmor(4, (int)(originalDamage * 4.0D + random.nextFloat() * originalDamage * 2.0D)); 
    float f2 = (float)(originalDamage + d2 + d);
    manager.damageArmors(f2);
    absorptionModifier = -absorptionModifier;
    this.bot.getHealthManager().setAbsHearts(Math.max(this.bot.getHealthManager().getAbsHearts() - absorptionModifier, 0.0F));
    if (f == 0.0F)
      return; 
    this.bot.getHealthManager().removeHealth((int)f);
  }
  
  public float applyArmor(DamageSource damagesource, float f, InventoryManager manager) {
    int br = br(manager);
    double i = 25.0D - br;
    float f1 = f * (float)i;
    f = f1 / 25.0F;
    return f;
  }
  
  public int br(InventoryManager manager) {
    int i = 0;
    ItemStack[] aitemstack = manager.getArmorStacks();
    int j = aitemstack.length;
    for (ItemStack itemstack : aitemstack) {
      if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
        int l = ((ItemArmor)itemstack.getItem()).c;
        i += l;
      } 
    } 
    return i;
  }
  
  public int br(EntityPlayer player) {
    int i = 0;
    ItemStack[] aitemstack = player.getEquipment();
    int j = aitemstack.length;
    for (ItemStack itemstack : aitemstack) {
      if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
        int l = ((ItemArmor)itemstack.getItem()).c;
        i += l;
      } 
    } 
    return i;
  }
  
  public float applyEnchants(DamageSource damagesource, float f, InventoryManager manager) {
    if (f <= 0.0F)
      return 0.0F; 
    int i = EnchantmentManager.a(manager.getArmorStacks(), damagesource);
    if (i > 20)
      i = 20; 
    if (i > 0) {
      int j = 25 - i;
      float f1 = f * j;
      f = f1 / 25.0F;
    } 
    return f;
  }
}


/* Location:              C:\Users\Adem\Desktop\estabotprac\plugins\AppToSus.jar!\mc\apptoeat\com\bot\bots\mechanics\DamageCalculator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */