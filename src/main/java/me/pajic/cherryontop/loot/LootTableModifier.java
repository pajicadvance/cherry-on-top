package me.pajic.cherryontop.loot;

import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootTableModifier {
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (Main.CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading()) {
                    for (String s : Main.CONFIG.enchantmentUpgrading.templateLootLocations()) {
                        if (ResourceLocation.parse(s).equals(key.location())) {
                            LootPool.Builder lootPool = LootPool.lootPool()
                                    .add(LootItem.lootTableItem(ModItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE).setWeight(Main.CONFIG.enchantmentUpgrading.templateLootChance()))
                                    .add(EmptyLootItem.emptyItem().setWeight(100 - Main.CONFIG.enchantmentUpgrading.templateLootChance()));
                            tableBuilder.withPool(lootPool);
                        }
                    }
                }
                if (Main.CONFIG.musicDiscLoot.enableMusicDiscLoot()) {
                    int discWeight = Main.CONFIG.musicDiscLoot.musicDiscLootChance();
                    int emptyWeight = 1200 - discWeight * 12;
                    for (String s : Main.CONFIG.musicDiscLoot.musicDiscLootLocations()) {
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
                if (Main.CONFIG.bottleOEnchantingImprovements.enableBottleOEnchantingImprovements() && Main.CONFIG.bottleOEnchantingImprovements.addToLootChests()) {
                    for (String s : Main.CONFIG.bottleOEnchantingImprovements.bottleLootLocations()) {
                        String[] stringArray = s.split(";");
                        if (stringArray.length == 2) {
                            if (ResourceLocation.parse(stringArray[0]).equals(key.location())) {
                                int count;
                                try {
                                    count = Integer.parseInt(stringArray[1]);
                                } catch (NumberFormatException e) {
                                    count = 1;
                                }
                                LootPool.Builder lootPool = LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))));
                                tableBuilder.withPool(lootPool);
                            }
                        }
                        if (stringArray.length == 1) {
                            if (ResourceLocation.parse(stringArray[0]).equals(key.location())) {
                                LootPool.Builder lootPool = LootPool.lootPool()
                                        .add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(Main.CONFIG.bottleOEnchantingImprovements.bottleLootChance()))
                                        .add(EmptyLootItem.emptyItem().setWeight(100 - Main.CONFIG.bottleOEnchantingImprovements.bottleLootChance()));
                                tableBuilder.withPool(lootPool);
                            }
                        }
                    }
                }
            }
        });
    }
}
