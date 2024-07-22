package me.pajic.cherryontop;

import me.pajic.cherryontop.config.CoTConfig;
import me.pajic.cherryontop.item.CoTItems;
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

    public static final CoTConfig CONFIG = CoTConfig.createAndLoad();

    @Override
    public void onInitialize() {

        FabricLoader.getInstance().getModContainer("cherry-on-top").ifPresent(modContainer -> {

            ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.parse("cherry-on-top:patcherlib"),
                    modContainer,
                    ResourcePackActivationType.ALWAYS_ENABLED
            );

            if (CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:enchantmentupgrade"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
                Registry.register(
                        BuiltInRegistries.ITEM,
                        ResourceLocation.parse("cherry-on-top:enchantment_upgrade"),
                        CoTItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE
                );
                ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(contents -> contents.addAfter(
                        Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE,
                        CoTItems.ENCHANTMENT_UPGRADE_SMITHING_TEMPLATE)
                );
            }

            if (CONFIG.infinityMendingCompatible()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:infinimending"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }

            if (CONFIG.musicDiscLoot.enableMusicDiscLoot()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:musicdiscloot"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (CONFIG.musicDiscLoot.remove13AndCatSimpleDungeonEntries()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:remove13andcatloot"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
            }

            if (Main.CONFIG.bottleOEnchantingImprovements.enableBottleOEnchantingImprovements()) {
                if (CONFIG.bottleOEnchantingImprovements.additionalChestLoot()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:bottleoenchantingloot"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
                if (CONFIG.bottleOEnchantingImprovements.renameToExperienceBottle()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:bottleoenchantingrename"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
            }

            if (Main.CONFIG.enchantedBookLootImprovements.enableEnchantedBookLootImprovements()) {
                if (Main.CONFIG.enchantedBookLootImprovements.additionalChestLoot()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:additionalbookloot"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
                if (Main.CONFIG.enchantedBookLootImprovements.structureSpecificLoot()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:structurespecificbookloot"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
            }

            if (Main.CONFIG.cryingObsidianRecipe()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:cryingobsidianrecipe"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }

            if (Main.CONFIG.glowstoneDustRecipe()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:glowstonedustrecipe"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
        });
    }
}
