package me.pajic.cherryontop.loot;

import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class LootTableModifier {
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (Main.CONFIG.enableEnchantmentUpgrading()) {
                    for (String s : Main.CONFIG.enchantmentUpgradingOptions.templateLootLocations()) {
                        if (ResourceLocation.parse(s).equals(key.location())) {
                            LootPool.Builder lootPool = LootPool.lootPool()
                                    .add(LootItem.lootTableItem(ModItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE).setWeight(Main.CONFIG.enchantmentUpgradingOptions.templateLootChance()))
                                    .add(EmptyLootItem.emptyItem().setWeight(100 - Main.CONFIG.enchantmentUpgradingOptions.templateLootChance()));
                            tableBuilder.withPool(lootPool);
                        }
                    }
                }
                if (Main.CONFIG.enableMusicDiscLoot()) {
                    int discWeight = Main.CONFIG.musicDiscLootOptions.musicDiscLootChance();
                    int emptyWeight = 1200 - discWeight * 12;
                    for (String s : Main.CONFIG.musicDiscLootOptions.musicDiscLootLocations()) {
                        if (ResourceLocation.parse(s).equals(key.location())) {
                            LootPool.Builder musicDiscPoolBuilder = LootPool.lootPool()
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_13).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_BLOCKS).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_CHIRP).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_FAR).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_MALL).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_MELLOHI).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_STAL).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_STRAD).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_WARD).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_11).setWeight(discWeight))
                                    .add(LootItem.lootTableItem(Items.MUSIC_DISC_WAIT).setWeight(discWeight))
                                    .add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
                            tableBuilder.withPool(musicDiscPoolBuilder);
                        }
                    }
                }
            }
        });
    }
}
