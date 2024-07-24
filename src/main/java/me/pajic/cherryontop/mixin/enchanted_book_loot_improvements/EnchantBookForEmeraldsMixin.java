package me.pajic.cherryontop.mixin.enchanted_book_loot_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTUtil;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerTrades.EnchantBookForEmeralds.class)
public class EnchantBookForEmeraldsMixin {

    @ModifyExpressionValue(
            method = "getOffer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;nextInt(Lnet/minecraft/util/RandomSource;II)I"
            )
    )
    private int setEnchantmentLevelFromPool(int original,
                                            @Local Holder<Enchantment> enchantmentHolder,
                                            @Local(argsOnly = true) RandomSource randomSource
    ) {
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.enchantmentLevelWeights()
        ) {
            return CoTUtil.calculateNewEnchantmentLevelFromWeights(enchantmentHolder, randomSource);
        }
        return original;
    }
}