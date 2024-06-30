package me.pajic.cherryontop.mixin.bottle_o_enchanting_improvements;

import me.pajic.cherryontop.Main;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ThrownExperienceBottle.class)
public class ThrownExperienceBottleMixin {

    @ModifyArg(
            method = "onHit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"
            ),
            index = 2
    )
    private int setXpDropAmount(int amount) {
        if (Main.CONFIG.bottleOEnchantingImprovements.enableBottleOEnchantingImprovements() && Main.CONFIG.bottleOEnchantingImprovements.modifyExperienceReward()) {
            ThrownExperienceBottle instance = (ThrownExperienceBottle) (Object) this;
            Function rand = new Function("rand", 1) {
                @Override
                public double apply(double... args) {
                    return 1 + instance.level().random.nextInt((int) args[0]);
                }
            };
            try {
                Expression expression = new ExpressionBuilder(Main.CONFIG.bottleOEnchantingImprovements.experienceReward())
                        .function(rand)
                        .build();
                if (expression.validate().isValid()) {
                    int newValue = (int) expression.evaluate();
                    if (newValue > 0) {
                        return newValue;
                    }
                }
            } catch (Exception e) {
                return amount;
            }
        }
        return amount;
    }
}
