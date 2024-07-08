package me.pajic.cherryontop.mixin.enchanted_book_loot_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTEnchantmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SetEnchantmentsFunction.class)
public class SetEnchantmentsFunctionMixin {

    @ModifyExpressionValue(
            method = {
                    "method_57656",
                    "method_60297"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;getInt(Lnet/minecraft/world/level/storage/loot/LootContext;)I"
            )
    )
    private static int setMaxEnchantmentLevel(int original,
                                              @Local(argsOnly = true) Holder<Enchantment> enchantmentHolder,
                                              @Local(argsOnly = true) LootContext context
    ) {
        RegistryAccess registryAccess = context.getLevel().registryAccess();
        if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements() &&
                Main.CONFIG.enchantedBookLootImprovements.modifyMaxEnchantmentLevel()
        ) {
            return CoTEnchantmentUtil.getNewMaxLevel(enchantmentHolder, registryAccess);
        }
        return original;
    }
}
