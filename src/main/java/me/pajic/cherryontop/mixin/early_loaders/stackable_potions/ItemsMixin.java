package me.pajic.cherryontop.mixin.early_loaders.stackable_potions;

import me.pajic.cherryontop.config.EarlyLoaderConfig;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public class ItemsMixin {

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(
                            value = "NEW",
                            target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/PotionItem;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 0
            )
    )
    private static int modifyPotionMaxStackSize(int original) {
        if (EarlyLoaderConfig.options.enableStackablePotions) {
            return EarlyLoaderConfig.options.potionMaxStackSize;
        }
        return original;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(
                            value = "NEW",
                            target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/SplashPotionItem;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 0
            )
    )
    private static int modifySplashPotionMaxStackSize(int original) {
        if (EarlyLoaderConfig.options.enableStackablePotions) {
            return EarlyLoaderConfig.options.potionMaxStackSize;
        }
        return original;
    }

    @ModifyArg(
            method = "<clinit>",
            slice = @Slice(
                    from = @At(
                            value = "NEW",
                            target = "(Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/LingeringPotionItem;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;",
                    ordinal = 0
            )
    )
    private static int modifyLingeringPotionMaxStackSize(int original) {
        if (EarlyLoaderConfig.options.enableStackablePotions) {
            return EarlyLoaderConfig.options.potionMaxStackSize;
        }
        return original;
    }
}
