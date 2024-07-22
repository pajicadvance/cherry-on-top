package me.pajic.cherryontop.mixin.enchanted_book_loot_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTEnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {

    @ModifyExpressionValue(
            method = "enchantItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;nextInt(Lnet/minecraft/util/RandomSource;II)I"
            )
    )
    private static int setEnchantmentLevelFromPool(int original,
                                                   @Local(argsOnly = true) Holder<Enchantment> enchantmentHolder,
                                                   @Local(argsOnly = true) RandomSource randomSource
    ) {
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.enchantmentLevelWeights()
        ) {
            return CoTEnchantmentUtil.calculateNewLevelFromWeights(enchantmentHolder, randomSource);
        }
        return original;
    }
}
