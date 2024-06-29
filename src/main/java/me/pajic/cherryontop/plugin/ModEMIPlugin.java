package me.pajic.cherryontop.plugin;

import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import me.pajic.cherryontop.Main;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public class ModEMIPlugin implements EmiPlugin {
    @Override
    public void initialize(EmiInitRegistry registry) {
        if (Main.CONFIG.enchantmentDisabler.enableEnchantmentDisabler()) {
            registry.disableStacks(stack -> {
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

    @Override
    public void register(EmiRegistry registry) {}
}
