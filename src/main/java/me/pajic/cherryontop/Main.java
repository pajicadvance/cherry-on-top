package me.pajic.cherryontop;

import me.pajic.cherryontop.config.ModConfig;
import me.pajic.cherryontop.item.ModItems;
import me.pajic.cherryontop.loot.LootTableModifier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();

    @Override
    public void onInitialize() {
        LootTableModifier.modifyLootTables();

        FabricLoader.getInstance().getModContainer("cherry-on-top").ifPresent(modContainer -> {
            if (CONFIG.infinityMendingCompatible()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:infinimending"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:enchantmentupgrade"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
                Registry.register(
                        BuiltInRegistries.ITEM,
                        ResourceLocation.parse("cherry-on-top:enchantment_upgrade"),
                        ModItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE
                );
                ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(contents -> contents.addAfter(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        ModItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE)
                );
            }
            if (CONFIG.musicDiscLoot.enableMusicDiscLoot() && CONFIG.musicDiscLoot.remove13AndCatSimpleDungeonEntries()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:remove13andcatloot"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
            if (CONFIG.enchantmentDisabler.enableEnchantmentDisabler()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:enchantmentdisabler"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
        });
    }
}
