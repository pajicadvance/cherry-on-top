package me.pajic.cherryontop.mixin.enchantment_disabler;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {

    @Shadow
    @Final
    private ContainerLevelAccess access;

    @Shadow
    @Final
    private RandomSource random;

    @SuppressWarnings("unchecked")
    @ModifyExpressionValue(
            method = "slotsChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isEnchantable()Z"
            )
    )
    private boolean dontUpdateIfNoEnchantmentsAvailable(boolean original, @Local ItemStack itemStack) {
        if (Main.CONFIG.enchantmentDisabler.enableEnchantmentDisabler() && !Main.CONFIG.disableEnchantingTable()) {
            final boolean[] con = {true};
            access.execute((level, pos) -> {
                Optional<HolderSet.Named<Enchantment>> possibleEnchantments =
                        level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getTag(EnchantmentTags.IN_ENCHANTING_TABLE);
                List<EnchantmentInstance> list = EnchantmentHelper.getAvailableEnchantmentResults(
                        EnchantmentHelper.getEnchantmentCost(random, 2, 15, itemStack),
                        itemStack,
                        ((HolderSet.Named)possibleEnchantments.get()).stream()
                );
                if (list.isEmpty()) con[0] = false;
            });
            return original && con[0];
        }
        else {
            return original;
        }
    }
}
