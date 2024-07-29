package me.pajic.cherryontop.mixin.whetstone;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.item.CoTItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @ModifyExpressionValue(
            method = "getComponentType",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
            )
    )
    private static boolean storeEnchantmentsIfWhetstone(boolean original, @Local(argsOnly = true) ItemStack stack) {
        if (Main.CONFIG.whetstone.enableWhetstone()) {
            return original || stack.is(CoTItems.WHETSTONE);
        }
        return original;
    }
}
