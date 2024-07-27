package me.pajic.cherryontop.mixin.disable_night_vision;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SetStewEffectFunction.class)
public class SetStewEffectFunctionMixin {

    @ModifyArg(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;update(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;Ljava/lang/Object;Ljava/util/function/BiFunction;)Ljava/lang/Object;"
            ),
            index = 2
    )
    private <T > T replaceNightVisionWithInvisibility(T updateValue, @Local int i) {
        if (Main.CONFIG.disableNightVision()) {
            SuspiciousStewEffects.Entry entry = (SuspiciousStewEffects.Entry) updateValue;
            if (entry.effect().is(MobEffects.NIGHT_VISION.unwrapKey().get())) {
                return (T) new SuspiciousStewEffects.Entry(MobEffects.INVISIBILITY, i);
            }
        }
        return updateValue;
    }
}
