package me.pajic.cherryontop.mixin.enchantment_upgrade_smithing_template;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.config.ModConfig;
import me.pajic.cherryontop.item.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingScreen.class)
public abstract class SmithingScreenMixin extends ItemCombinerScreen<SmithingMenu> {

    public SmithingScreenMixin(SmithingMenu itemCombinerMenu, Inventory inventory, Component component, ResourceLocation resourceLocation) {
        super(itemCombinerMenu, inventory, component, resourceLocation);
    }

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void renderEnchantmentCostText(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        if (ModConfig.enableEnchantmentUpgrading && ModConfig.upgradingHasExperienceCost &&
                menu.slots.get(0).getItem().is(ModItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE) &&
                menu.slots.get(1).getItem().is(Items.ENCHANTED_BOOK) &&
                menu.slots.get(1).getItem().has(DataComponents.STORED_ENCHANTMENTS) &&
                menu.slots.get(2).getItem().is(Items.LAPIS_LAZULI) &&
                menu.slots.get(3).hasItem()
        ) {
            ItemStack stack = menu.slots.get(1).getItem();
            int repairCost = ModConfig.upgradingBaseExperienceCost + stack.getOrDefault(DataComponents.REPAIR_COST, 0);
            Component component = Component.translatable("container.repair.cost", repairCost);
            int m = this.imageWidth + 118 - this.font.width(component) - 2;
            int l = menu.slots.get(3).mayPickup(minecraft.player) ? 8453920 : 0xFF6060;
            guiGraphics.fill(m - 2, 105, this.imageWidth + 118, 117, 0x4F000000);
            guiGraphics.drawString(this.font, component, m, 107, l);
        }
    }

    @ModifyExpressionValue(
            method = "renderBg",
            at = @At(value = "CONSTANT", args = "intValue=75")
    )
    private int nudgeArmorStandUp(int original) {
        if (ModConfig.enableEnchantmentUpgrading && ModConfig.upgradingHasExperienceCost) {
            return original - 10;
        }
        return original;
    }
}
