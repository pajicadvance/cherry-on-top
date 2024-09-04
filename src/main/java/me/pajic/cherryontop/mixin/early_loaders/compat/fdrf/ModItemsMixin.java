package me.pajic.cherryontop.mixin.early_loaders.compat.fdrf;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.cherryontop.config.EarlyLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vectorwing.farmersdelight.common.registry.ModItems;

@IfModLoaded("farmersdelight")
@Mixin(ModItems.class)
public class ModItemsMixin {

    @ModifyArg(
            method = "bowlFoodItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"
            )
    )
    private static int modifyBowlFoodItemStackSize(int original) {
        if (EarlyLoader.CONFIG.fd_enableStackableBowlFoods) {
            return EarlyLoader.CONFIG.fd_bowlFoodMaxStackSize;
        }
        return original;
    }

    @ModifyArg(
            method = "drinkItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"
            )
    )
    private static int modifyDrinkItemStackSize(int original) {
        if (EarlyLoader.CONFIG.fd_enableStackableDrinks) {
            return EarlyLoader.CONFIG.fd_drinkMaxStackSize;
        }
        return original;
    }

    @ModifyArg(
            method = "lambda$static$129",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"
            )
    )
    private static int modifyGlowBerryCustardMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.fd_enableStackableDrinks) {
            return EarlyLoader.CONFIG.fd_drinkMaxStackSize;
        }
        return original;
    }
}
