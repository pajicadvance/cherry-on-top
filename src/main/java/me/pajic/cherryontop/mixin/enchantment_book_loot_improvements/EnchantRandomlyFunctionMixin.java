package me.pajic.cherryontop.mixin.enchantment_book_loot_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTEnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {

    @Unique
    private static RegistryAccess registryAccess;

    @Inject(
            method = "run",
            at = @At("HEAD")
    )
    private void grabRegistryAccess(ItemStack stack, LootContext context, CallbackInfoReturnable<ItemStack> cir) {
        registryAccess = context.getLevel().registryAccess();
    }

    @ModifyArg(
            method = "enchantItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;nextInt(Lnet/minecraft/util/RandomSource;II)I"
            ),
            index = 2
    )
    private static int setMaxEnchantmentLevel(int original, @Local(argsOnly = true) Holder<Enchantment> enchantmentHolder) {
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.modifyMaxEnchantmentLevel()
        ) {
            return CoTEnchantmentUtil.getNewMaxLevel(enchantmentHolder, registryAccess);
        }
        return original;
    }

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
            return CoTEnchantmentUtil.calculateNewLevelFromWeights(enchantmentHolder, randomSource, registryAccess);
        }
        return original;
    }
}
