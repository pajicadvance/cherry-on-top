package me.pajic.cherryontop.plugin;

import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.recipe.EmiAnvilRecipe;
import me.pajic.cherryontop.item.CoTItems;
import net.minecraft.world.item.Items;

public class CoTEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipe(new EmiAnvilRecipe(EmiStack.of(CoTItems.WHETSTONE), EmiStack.of(Items.QUARTZ),
                EmiPort.id("emi", "/" + "anvil/repairing/material" + "/" + EmiUtil.subId(CoTItems.WHETSTONE) + "/" + EmiUtil.subId(Items.QUARTZ))
        ));
    }
}
