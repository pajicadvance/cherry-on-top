package me.pajic.cherryontop.compat;

import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.recipe.EmiAnvilRecipe;
import dev.emi.emi.recipe.EmiBrewingRecipe;
import me.pajic.cherryontop.item.CoTItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

public class CoTEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipe(new EmiAnvilRecipe(
                EmiStack.of(CoTItems.WHETSTONE),
                EmiStack.of(Items.QUARTZ),
                EmiPort.id(
                        "emi",
                        "/" + "anvil/repairing/material" +
                                "/" + EmiUtil.subId(CoTItems.WHETSTONE) +
                                "/" + EmiUtil.subId(Items.QUARTZ)
                )
        ));

        ItemStack awkwardPotion = new ItemStack(Items.POTION);
        awkwardPotion.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.AWKWARD));

        registry.addRecipe(new EmiBrewingRecipe(
                EmiStack.of(awkwardPotion),
                EmiStack.of(Items.ENDER_PEARL),
                EmiStack.of(CoTItems.POTION_OF_TELEPORTATION),
                EmiPort.id(
                        "emi",
                        "/" + "brewing/item" +
                                "/" + EmiUtil.subId(awkwardPotion.getItem()) +
                                "/" + EmiUtil.subId(Items.ENDER_PEARL) +
                                "/" + EmiUtil.subId(CoTItems.POTION_OF_TELEPORTATION)
                )
        ));

        registry.addRecipe(new EmiBrewingRecipe(
                EmiStack.of(awkwardPotion),
                EmiStack.of(Items.ENDER_EYE),
                EmiStack.of(CoTItems.POTION_OF_WORMHOLE),
                EmiPort.id(
                        "emi",
                        "/" + "brewing/item" +
                                "/" + EmiUtil.subId(awkwardPotion.getItem()) +
                                "/" + EmiUtil.subId(Items.ENDER_EYE) +
                                "/" + EmiUtil.subId(CoTItems.POTION_OF_WORMHOLE)
                )
        ));
    }
}
