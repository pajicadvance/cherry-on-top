package me.pajic.cherryontop.mixin.enchantment_disabler;

import me.pajic.cherryontop.Main;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Set;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {

    @Shadow
    private Collection<ItemStack> displayItems;

    @Shadow
    private Set<ItemStack> displayItemsSearchTab;

    @Inject(method = "buildContents", at = @At("TAIL"))
    private void removeDisabledEnchantmentBooks(CallbackInfo ci) {
        if (Main.CONFIG.enchantmentDisabler.enableEnchantmentDisabler()) {
            filter(displayItems);
            filter(displayItemsSearchTab);
        }
    }

    @Unique
    private void filter(Collection<ItemStack> displayItems) {
        displayItems.removeIf(stack -> {
            for (String s : Main.CONFIG.enchantmentDisabler.disabledEnchantments()) {
                ItemEnchantments storedEnchantments = stack.get(DataComponents.STORED_ENCHANTMENTS);
                if (storedEnchantments != null && storedEnchantments.keySet().stream().anyMatch(holder -> holder.is(ResourceLocation.parse(s)))) {
                    return true;
                }
            }
            return false;
        });
    }
}
