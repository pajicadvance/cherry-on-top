package me.pajic.cherryontop.mixin.enchanted_book_loot_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTEnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
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
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"
            )
    )
    private int setMaxEnchantmentLevel(int original, @Local Holder<Enchantment> enchantmentHolder, @Local(argsOnly = true) Entity trader) {
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.modifyMaxEnchantmentLevel()
        ) {
            RegistryAccess registryAccess = trader.level().registryAccess();
            return Math.min(original, CoTEnchantmentUtil.getNewMaxLevel(enchantmentHolder, registryAccess));
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "getOffer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;nextInt(Lnet/minecraft/util/RandomSource;II)I"
            )
    )
    private int setEnchantmentLevelFromPool(int original,
                                            @Local Holder<Enchantment> enchantmentHolder,
                                            @Local(argsOnly = true) RandomSource randomSource,
                                            @Local(argsOnly = true) Entity trader
    ) {
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.enchantmentLevelWeights()
        ) {
            return CoTEnchantmentUtil.calculateNewLevelFromWeights(enchantmentHolder, randomSource, trader.level().registryAccess());
        }
        return original;
    }
}