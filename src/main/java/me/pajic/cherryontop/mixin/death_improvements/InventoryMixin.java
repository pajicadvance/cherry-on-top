package me.pajic.cherryontop.mixin.death_improvements;

import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.data.CoTData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Inventory.class)
public class InventoryMixin {

    @ModifyArgs(
            method = "dropAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;"
            )
    )
    private void preventItemSplatterAndSetNoDespawnComponent(Args args) {
        if (Main.CONFIG.deathImprovements.noItemSplatterOnDeath()) {
            args.set(1, false);
        }
        if (Main.CONFIG.deathImprovements.noDeathItemDespawn()) {
            ItemStack droppedStack = args.get(0);
            droppedStack.set(CoTData.NO_DESPAWN, true);
            args.set(0, droppedStack);
        }
    }

    @ModifyArg(
            method = "add(Lnet/minecraft/world/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Inventory;add(ILnet/minecraft/world/item/ItemStack;)Z"
            ),
            index = 1
    )
    private ItemStack removeNoDespawnComponentOnPickup(ItemStack stack) {
        if (Main.CONFIG.deathImprovements.noDeathItemDespawn()) {
            stack.remove(CoTData.NO_DESPAWN);
        }
        return stack;
    }
}
