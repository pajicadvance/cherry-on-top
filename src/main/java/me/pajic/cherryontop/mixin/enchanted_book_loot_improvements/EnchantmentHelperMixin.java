package me.pajic.cherryontop.mixin.enchanted_book_loot_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTEnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @ModifyExpressionValue(
            method = "method_60106",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"
            )
    )
    private static int setMaxEnchantmentLevel(int original, @Local(argsOnly = true) Holder<Enchantment> enchantmentHolder) {
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.modifyMaxEnchantmentLevel()
        ) {
            return CoTEnchantmentUtil.getNewMaxLevel(enchantmentHolder, Main.SERVER.registryAccess());
        }
        return original;
    }
}
