package me.pajic.cherryontop.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.item.CoTItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoTUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger("CherryOnTop-Util");

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
        if (Main.CONFIG.anvilImprovements.modifyAnvilRepairUnitCosts()) {

            if (itemStack.is(ItemTags.HEAD_ARMOR)) return Main.CONFIG.anvilImprovements.armor.headArmorUnits();
            if (itemStack.is(ItemTags.CHEST_ARMOR)) return Main.CONFIG.anvilImprovements.armor.chestArmorUnits();
            if (itemStack.is(ItemTags.LEG_ARMOR)) return Main.CONFIG.anvilImprovements.armor.legArmorUnits();
            if (itemStack.is(ItemTags.FOOT_ARMOR)) return Main.CONFIG.anvilImprovements.armor.footArmorUnits();

            if (itemStack.is(ItemTags.PICKAXES)) return Main.CONFIG.anvilImprovements.tools.pickaxeUnits();
            if (itemStack.is(ItemTags.AXES)) return Main.CONFIG.anvilImprovements.tools.axeUnits();
            if (itemStack.is(ItemTags.SWORDS)) return Main.CONFIG.anvilImprovements.tools.swordUnits();
            if (itemStack.is(ItemTags.HOES)) return Main.CONFIG.anvilImprovements.tools.hoeUnits();
            if (itemStack.is(ItemTags.SHOVELS)) return Main.CONFIG.anvilImprovements.tools.shovelUnits();

            if (itemStack.is(Items.SHIELD)) return Main.CONFIG.anvilImprovements.uniqueItems.shieldUnits();
            if (itemStack.is(Items.ELYTRA)) return Main.CONFIG.anvilImprovements.uniqueItems.elytraUnits();
            if (itemStack.is(Items.MACE)) return Main.CONFIG.anvilImprovements.uniqueItems.maceUnits();
            if (itemStack.is(CoTItems.WHETSTONE)) return Main.CONFIG.anvilImprovements.uniqueItems.whetstoneUnits();

            for (String s : Main.CONFIG.anvilImprovements.modTags()) {
                String[] sArray = s.split(";");
                if (sArray.length != 2) {
                    LOGGER.error("Invalid repair unit cost item tag entry: {}, skipping", s);
                }
                else if (itemStack.is(TagKey.create(Registries.ITEM, ResourceLocation.parse(sArray[0])))) {
                    return Integer.parseInt(sArray[1]);
                }
            }

            for (String s : Main.CONFIG.anvilImprovements.modItems()) {
                String[] sArray = s.split(";");
                if (sArray.length != 2) {
                    LOGGER.error("Invalid repair unit cost item entry: {}, skipping", s);
                }
                else if (itemStack.is(BuiltInRegistries.ITEM.get(ResourceLocation.parse(sArray[0])))) {
                    return Integer.parseInt(sArray[1]);
                }
            }
        }

        return 4;
    }

    public static void openEnderBackpack(Player player) {
        PlayerEnderChestContainer container = player.getEnderChestInventory();
        player.openMenu(new SimpleMenuProvider((i, inventory, player1) ->
                ChestMenu.threeRows(i, inventory, container), Component.translatable("container.enderchest")
        ));
        player.awardStat(Stats.OPEN_ENDERCHEST);
    }
}
