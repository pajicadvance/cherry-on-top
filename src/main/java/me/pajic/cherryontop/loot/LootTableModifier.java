package me.pajic.cherryontop.loot;

import me.pajic.cherryontop.config.ModConfig;
import me.pajic.cherryontop.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class LootTableModifier {
    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (ModConfig.enableEnchantmentUpgrading) {
                    for (String s : ModConfig.templateLootLocations) {
                        if (ResourceLocation.parse(s).equals(key.location())) {
                            LootPool.Builder lootPool = LootPool.lootPool()
                                    .add(LootItem.lootTableItem(ModItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE).setWeight(ModConfig.templateLootChance))
                                    .add(EmptyLootItem.emptyItem().setWeight(100 - ModConfig.templateLootChance));
                            tableBuilder.withPool(lootPool);
                        }
                    }
                }
            }
        });
    }
}
