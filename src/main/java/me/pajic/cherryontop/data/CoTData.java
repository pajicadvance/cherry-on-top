package me.pajic.cherryontop.data;

import me.pajic.cherryontop.Main;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class CoTData {

    public static void initData() {
        FabricLoader.getInstance().getModContainer("cherry-on-top").ifPresent(modContainer -> {

            ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.parse("cherry-on-top:patcherlib"),
                    modContainer,
                    ResourcePackActivationType.ALWAYS_ENABLED
            );

            if (Main.CONFIG.enderBackpack.enableEnderBackpack()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:enderbackpack"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (FabricLoader.getInstance().isModLoaded("trinkets")) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:trinketscompat"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
            }

            if (Main.CONFIG.enchantmentUpgrading.enableEnchantmentUpgrading()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:enchantmentupgrade"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }

            if (Main.CONFIG.whetstone.enableWhetstone()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:whetstone"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }

            if (Main.CONFIG.infinityMendingCompatible()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:infinimending"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }

            if (Main.CONFIG.musicDiscLoot.enableMusicDiscLoot()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:musicdiscloot"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
                if (Main.CONFIG.musicDiscLoot.remove13AndCatLootEntries()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:remove13andcatloot"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
            }

            if (Main.CONFIG.bottleOEnchantingImprovements.enableBottleOEnchantingImprovements()) {
                if (Main.CONFIG.bottleOEnchantingImprovements.additionalChestLoot()) {
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            ResourceLocation.parse("cherry-on-top:bottleoenchantingloot"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    );
                }
                if (Main.CONFIG.bottleOEnchantingImprovements.renameToExperienceBottle()) {
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

            if (Main.CONFIG.disableNightVision()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:disablenightvision"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }

            if (Main.CONFIG.soulSpeedNoDamage()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        ResourceLocation.parse("cherry-on-top:soulspeednodamage"),
                        modContainer,
                        ResourcePackActivationType.ALWAYS_ENABLED
                );
            }
        });
    }

}
