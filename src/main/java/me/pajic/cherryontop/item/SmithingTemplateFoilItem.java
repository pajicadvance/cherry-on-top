package me.pajic.cherryontop.item;

import me.pajic.cherryontop.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SmithingTemplateFoilItem extends SmithingTemplateItem {

    public SmithingTemplateFoilItem(Component component, Component component2, Component component3, Component component4, Component component5, List<ResourceLocation> list, List<ResourceLocation> list2, FeatureFlag... featureFlags) {
        super(component, component2, component3, component4, component5, list, list2, featureFlags);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isEnabled(@NotNull FeatureFlagSet enabledFeatures) {
        return Main.CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading();
    }
}
