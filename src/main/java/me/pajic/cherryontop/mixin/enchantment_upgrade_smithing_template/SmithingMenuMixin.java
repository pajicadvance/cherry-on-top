package me.pajic.cherryontop.mixin.enchantment_upgrade_smithing_template;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.item.CoTItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {

    public SmithingMenuMixin(@Nullable MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
        super(menuType, i, inventory, containerLevelAccess);
    }

    @Unique
    int cost = 0;

    @Inject(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;enabledFeatures()Lnet/minecraft/world/flag/FeatureFlagSet;"
            ),
            cancellable = true
    )
    private void incrementEnchantmentLevel(CallbackInfo ci, @Local LocalRef<ItemStack> stack) {
        if (Main.CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading() && isEnchantmentUpgradeRecipe()) {
            boolean success = false;
            int lapisAmount = slots.get(2).getItem().getCount();
            ItemEnchantments itemEnchantments = stack.get().get(DataComponents.STORED_ENCHANTMENTS);
            if (itemEnchantments != null && lapisAmount <= itemEnchantments.entrySet().size() &&
                    (!Main.CONFIG.enchantmentUpgrading.allowUpgradingSingleEnchantedBooksOnly() || itemEnchantments.entrySet().size() == 1)) {
                ItemStack updatedStack = stack.get();
                int counter = 0;
                for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
                    counter++;
                    if (counter == lapisAmount && entry.getIntValue() < entry.getKey().value().getMaxLevel()) {
                        EnchantmentHelper.updateEnchantments(updatedStack, mutable ->
                                mutable.upgrade(entry.getKey(), entry.getIntValue() + 1));
                        if (Main.CONFIG.enchantmentUpgrading.upgradingHasExperienceCost()) {
                            int originalRepairCost = stack.get().getOrDefault(DataComponents.REPAIR_COST, 0);
                            cost = Main.CONFIG.enchantmentUpgrading.upgradingBaseExperienceCost() + originalRepairCost;
                            if (cost < 1 || (!Main.CONFIG.enchantmentUpgrading.ignoreTooExpensive() && cost >= 40)) break;
                            updatedStack.set(DataComponents.REPAIR_COST, AnvilMenu.calculateIncreasedRepairCost(originalRepairCost));
                        }
                        stack.set(updatedStack);
                        success = true;
                        break;
                    }
                }
            }
            if (!success) {
                resultSlots.setItem(0, ItemStack.EMPTY);
                ci.cancel();
            }
        }
    }

    @ModifyReturnValue(
            method = "mayPickup",
            at = @At("RETURN")
    )
    private boolean modifyMayPickup(boolean original) {
        if (Main.CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading() && Main.CONFIG.enchantmentUpgrading.upgradingHasExperienceCost()
                && isEnchantmentUpgradeRecipe()) {
            return (player.hasInfiniteMaterials() || player.experienceLevel >= cost) && cost > 0;
        }
        return original;
    }

    @Inject(
            method = "onTake",
            at = @At("HEAD")
    )
    private void hookOnTake(Player player, ItemStack itemStack, CallbackInfo ci) {
        if (Main.CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading() && Main.CONFIG.enchantmentUpgrading.upgradingHasExperienceCost() &&
                isEnchantmentUpgradeRecipe() && !player.getAbilities().instabuild) {
            player.giveExperienceLevels(-cost);
        }
    }

    @Unique
    private boolean isEnchantmentUpgradeRecipe() {
        return slots.get(0).getItem().is(CoTItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE) &&
                slots.get(1).getItem().is(Items.ENCHANTED_BOOK) &&
                slots.get(1).getItem().has(DataComponents.STORED_ENCHANTMENTS);
    }
}
