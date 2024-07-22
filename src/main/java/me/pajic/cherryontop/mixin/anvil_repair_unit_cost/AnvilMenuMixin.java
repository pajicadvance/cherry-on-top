package me.pajic.cherryontop.mixin.anvil_repair_unit_cost;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @ModifyArg(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Math;min(II)I"),
            index = 1
    )
    private int modifyRepairUnitCost(int original, @Local(ordinal = 1) ItemStack itemStack) {
        if (Main.CONFIG.anvilRepairUnitCost.modifyAnvilRepairUnitCosts()) {
            int unitCost = 4;

            if (itemStack.is(ItemTags.HEAD_ARMOR)) unitCost = Main.CONFIG.anvilRepairUnitCost.armor.headArmorUnits();
            else if (itemStack.is(ItemTags.CHEST_ARMOR)) unitCost = Main.CONFIG.anvilRepairUnitCost.armor.chestArmorUnits();
            else if (itemStack.is(ItemTags.LEG_ARMOR)) unitCost = Main.CONFIG.anvilRepairUnitCost.armor.legArmorUnits();
            else if (itemStack.is(ItemTags.FOOT_ARMOR)) unitCost = Main.CONFIG.anvilRepairUnitCost.armor.footArmorUnits();

            else if (itemStack.is(ItemTags.PICKAXES)) unitCost = Main.CONFIG.anvilRepairUnitCost.tools.pickaxeUnits();
            else if (itemStack.is(ItemTags.AXES)) unitCost = Main.CONFIG.anvilRepairUnitCost.tools.axeUnits();
            else if (itemStack.is(ItemTags.SWORDS)) unitCost = Main.CONFIG.anvilRepairUnitCost.tools.swordUnits();
            else if (itemStack.is(ItemTags.HOES)) unitCost = Main.CONFIG.anvilRepairUnitCost.tools.hoeUnits();
            else if (itemStack.is(ItemTags.SHOVELS)) unitCost = Main.CONFIG.anvilRepairUnitCost.tools.shovelUnits();

            else if (itemStack.is(Items.SHIELD)) unitCost = Main.CONFIG.anvilRepairUnitCost.uniqueItems.shieldUnits();
            else if (itemStack.is(Items.ELYTRA)) unitCost = Main.CONFIG.anvilRepairUnitCost.uniqueItems.elytraUnits();
            else if (itemStack.is(Items.MACE)) unitCost = Main.CONFIG.anvilRepairUnitCost.uniqueItems.maceUnits();

            return itemStack.getMaxDamage() / unitCost;
        }
        else {
            return original;
        }
    }
}
