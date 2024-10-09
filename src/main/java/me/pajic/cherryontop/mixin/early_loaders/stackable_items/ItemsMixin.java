package me.pajic.cherryontop.mixin.early_loaders.stackable_items;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.config.EarlyLoader;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Items.class)
public class ItemsMixin {

    @Definition(id = "RABBIT_STEW", field = "Lnet/minecraft/world/food/Foods;RABBIT_STEW:Lnet/minecraft/world/food/FoodProperties;")
    @Expression("new ?().?(@(?)).?(RABBIT_STEW)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyRabbitStewMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableStews) {
            return EarlyLoader.CONFIG.stewMaxStackSize;
        }
        return original;
    }

    @Definition(id = "MUSHROOM_STEW", field = "Lnet/minecraft/world/food/Foods;MUSHROOM_STEW:Lnet/minecraft/world/food/FoodProperties;")
    @Expression("new ?().?(@(?)).?(MUSHROOM_STEW)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyMushroomStewMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableStews) {
            return EarlyLoader.CONFIG.stewMaxStackSize;
        }
        return original;
    }

    @Definition(id = "BEETROOT_SOUP", field = "Lnet/minecraft/world/food/Foods;BEETROOT_SOUP:Lnet/minecraft/world/food/FoodProperties;")
    @Expression("new ?().?(@(?)).?(BEETROOT_SOUP)")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyBeetrootSoupMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableStews) {
            return EarlyLoader.CONFIG.stewMaxStackSize;
        }
        return original;
    }

    @Definition(id = "CAKE", field = "Lnet/minecraft/world/level/block/Blocks;CAKE:Lnet/minecraft/world/level/block/Block;")
    @Expression("new ?(CAKE, new ?().?(@(?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static int modifyCakeMaxStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableCakes) {
            return EarlyLoader.CONFIG.cakeMaxStackSize;
        }
        return original;
    }
}
