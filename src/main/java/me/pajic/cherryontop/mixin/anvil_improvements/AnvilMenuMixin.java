package me.pajic.cherryontop.mixin.anvil_improvements;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Shadow private int repairItemCountCost;

    @ModifyArg(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(II)I"),
            index = 1
    )
    private int modifyRepairUnitCost(int original, @Local(ordinal = 1) ItemStack itemStack) {
        if (Main.CONFIG.anvilImprovements.modifyAnvilRepairUnitCosts()) {
            return itemStack.getMaxDamage() / CoTUtil.determineUnitCost(itemStack);
        }
        else {
            return original;
        }
    }

    @ModifyExpressionValue(
            method = "method_24922",
            at = @At(
                    value = "CONSTANT",
                    args = "floatValue=0.12F"
            )
    )
    private static float modifyDegradationChance(float original) {
        if (Main.CONFIG.anvilImprovements.modifyDegradationChance()) {
            return Main.CONFIG.anvilImprovements.degradationChance() / 100;
        }
        return original;
    }

    @WrapWithCondition(
            method = "onTake",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V"
            )
    )
    private boolean noXPCostIfUnenchanted(Player instance, int levels, @Local(argsOnly = true) ItemStack itemStack) {
        return !Main.CONFIG.anvilImprovements.freeUnenchantedRepairs() || itemStack.isEnchanted();
    }

    @ModifyArg(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;clamp(JJJ)J"
            ),
            index = 0
    )
    private long modifyXPCost(long value, @Local long l, @Local(ordinal = 1) int j) {
        // deduct cost of rename from total cost if option is enabled
        if (Main.CONFIG.anvilImprovements.freeRenames()) {
            value -= j;
        }
        // if cost ends up consisting of just prior work cost, ignore it
        if (value == l) {
            value = 0;
        }
        // if there is an "actual" cost, deduct prior work cost from total cost if option is enabled
        if (value != 0 && Main.CONFIG.anvilImprovements.noPriorWorkCost()) {
            value -= l;
        }
        return value;
    }

    @ModifyExpressionValue(
            method = "mayPickup",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/DataSlot;get()I",
                    ordinal = 1
            )
    )
    private int allowTakingFreeRepairs(int original) {
        return original == 0 && repairItemCountCost >= 0 ? 1 : original;
    }

    @WrapOperation(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/AnvilMenu;calculateIncreasedRepairCost(I)I"
            )
    )
    private int preventPriorWorkCostIncrease(int oldRepairCost, Operation<Integer> original, @Local(ordinal = 1) ItemStack itemStack) {
        if (Main.CONFIG.anvilImprovements.noPriorWorkCost()) {
            return oldRepairCost;
        }
        else {
            return original.call(oldRepairCost);
        }
    }

    @ModifyExpressionValue(
            method = "createResult",
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
