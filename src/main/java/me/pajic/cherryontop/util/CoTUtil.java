package me.pajic.cherryontop.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.item.CoTItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public class CoTUtil {

    public static int calculateNewEnchantmentLevelFromWeights(Holder<Enchantment> enchantmentHolder, RandomSource randomSource) {
        int maxLevel = enchantmentHolder.value().getMaxLevel();

        if (maxLevel == 1) {
            return 1;
        }

        int[] weights = new int[] {
                Main.CONFIG.enchantedBookLootImprovements.level1Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level2Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level3Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level4Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level5Weight()
        };

        IntList pool = new IntArrayList();
        for (int i = 0; i < maxLevel; i++) {
            int j = weights[i];
            for (int k = 0; k < j; k++) {
                pool.add(i + 1);
            }
        }

        return pool.getInt(randomSource.nextInt(pool.size()));
    }

    public static int determineUnitCost(ItemStack itemStack) {
        int unitCost = 4;

        if (Main.CONFIG.anvilImprovements.modifyAnvilRepairUnitCosts()) {

            if (itemStack.is(ItemTags.HEAD_ARMOR)) unitCost = Main.CONFIG.anvilImprovements.armor.headArmorUnits();
            else if (itemStack.is(ItemTags.CHEST_ARMOR)) unitCost = Main.CONFIG.anvilImprovements.armor.chestArmorUnits();
            else if (itemStack.is(ItemTags.LEG_ARMOR)) unitCost = Main.CONFIG.anvilImprovements.armor.legArmorUnits();
            else if (itemStack.is(ItemTags.FOOT_ARMOR)) unitCost = Main.CONFIG.anvilImprovements.armor.footArmorUnits();

            else if (itemStack.is(ItemTags.PICKAXES)) unitCost = Main.CONFIG.anvilImprovements.tools.pickaxeUnits();
            else if (itemStack.is(ItemTags.AXES)) unitCost = Main.CONFIG.anvilImprovements.tools.axeUnits();
            else if (itemStack.is(ItemTags.SWORDS)) unitCost = Main.CONFIG.anvilImprovements.tools.swordUnits();
            else if (itemStack.is(ItemTags.HOES)) unitCost = Main.CONFIG.anvilImprovements.tools.hoeUnits();
            else if (itemStack.is(ItemTags.SHOVELS)) unitCost = Main.CONFIG.anvilImprovements.tools.shovelUnits();

            else if (itemStack.is(Items.SHIELD)) unitCost = Main.CONFIG.anvilImprovements.uniqueItems.shieldUnits();
            else if (itemStack.is(Items.ELYTRA)) unitCost = Main.CONFIG.anvilImprovements.uniqueItems.elytraUnits();
            else if (itemStack.is(Items.MACE)) unitCost = Main.CONFIG.anvilImprovements.uniqueItems.maceUnits();
            else if (itemStack.is(CoTItems.WHETSTONE)) unitCost = Main.CONFIG.anvilImprovements.uniqueItems.whetstoneUnits();
        }

        return unitCost;
    }

    public static void openEnderBackpack(Player player) {
        PlayerEnderChestContainer container = player.getEnderChestInventory();
        player.openMenu(new SimpleMenuProvider((i, inventory, player1) ->
                ChestMenu.threeRows(i, inventory, container), Component.translatable("container.enderchest")
        ));
        player.awardStat(Stats.OPEN_ENDERCHEST);
    }
}
