package me.pajic.cherryontop.mixin.anvil_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.cherryontop.Main;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin extends ItemCombinerScreen<AnvilMenu> {

    public AnvilScreenMixin(AnvilMenu menu, Inventory playerInventory, Component title, ResourceLocation menuResource) {
        super(menu, playerInventory, title, menuResource);
    }

    @WrapOperation(
            method = "renderLabels",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/AnvilMenu;getCost()I"
            )
    )
    private int noXPCostIfUnenchanted(AnvilMenu instance, Operation<Integer> original) {
        if (Main.CONFIG.anvilImprovements.freeUnenchantedRepairs() && !menu.getSlot(2).getItem().isEnchanted()) {
            return 0;
        }
        return original.call(instance);
    }

    @ModifyExpressionValue(
            method = "renderLabels",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=40"
            )
    )
    private int ignoreTooExpensive(int original) {
        if (Main.CONFIG.anvilImprovements.noTooExpensive()) {
            return Integer.MAX_VALUE;
        }
        return original;
    }
}
