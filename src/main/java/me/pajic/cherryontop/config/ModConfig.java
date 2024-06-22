package me.pajic.cherryontop.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;

public class ModConfig {

    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath("cherry-on-top", "mod_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("cherry-on-top.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build()
            ).build();

    @SerialEntry public static boolean enableEnchantmentUpgrading = false;
    @SerialEntry public static boolean upgradingHasExperienceCost = true;
    @SerialEntry public static int upgradingBaseExperienceCost = 9;
    @SerialEntry public static boolean allowUpgradingSingleEnchantedBooksOnly = false;
    @SerialEntry public static int templateLootChance = 10;
    @SerialEntry public static List<String> templateLootLocations = List.of("minecraft:chests/ancient_city");

    @SerialEntry public static boolean enablePhantomSpawningRework = false;
    @SerialEntry public static int phantomSpawnStartHeight = 128;
    @SerialEntry public static int phantomSpawnFrequencyBase = 30;
    @SerialEntry public static int phantomSpawnFrequencyRandomOffsetBound = 30;
    @SerialEntry public static boolean repelPhantomsWithDefinedItems = false;
    @SerialEntry public static List<Item> phantomRepellentItems = List.of(Items.PHANTOM_MEMBRANE);

    @SerialEntry public static boolean disableDoubleTapSprint = false;
    @SerialEntry public static boolean playBowDrawingSounds = false;
    @SerialEntry public static boolean craftTippedArrowsWithRegularPotions = false;
    @SerialEntry public static boolean infinityMendingCompatible = false;
    @SerialEntry public static boolean hideDebugInfoInSurvival = false;
    @SerialEntry public static boolean riptideWorksOnlyInWater = false;

    public static Screen makeScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("cherry-on-top.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("cherry-on-top.category.features.phantomSpawningRework"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.phantomSpawningRework.enablePhantomSpawningRework"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.phantomSpawningRework.enablePhantomSpawningRework.desc")))
                                .binding(enablePhantomSpawningRework, () -> enablePhantomSpawningRework, newValue -> enablePhantomSpawningRework = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.phantomSpawningRework.spawnStartHeight"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.phantomSpawningRework.spawnStartHeight.desc")))
                                .binding(phantomSpawnStartHeight, () -> phantomSpawnStartHeight, newValue -> phantomSpawnStartHeight = newValue)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(-64, 320).step(1))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.phantomSpawningRework.spawnFrequencyBase"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.phantomSpawningRework.spawnFrequencyBase.desc")))
                                .binding(phantomSpawnFrequencyBase, () -> phantomSpawnFrequencyBase, newValue -> phantomSpawnFrequencyBase = newValue)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.phantomSpawningRework.spawnFrequencyRandomOffsetBound"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.phantomSpawningRework.spawnFrequencyRandomOffsetBound.desc")))
                                .binding(phantomSpawnFrequencyRandomOffsetBound, () -> phantomSpawnFrequencyRandomOffsetBound, newValue -> phantomSpawnFrequencyRandomOffsetBound = newValue)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(1))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.phantomSpawningRework.repelPhantomsWithDefinedItems"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.phantomSpawningRework.repelPhantomsWithDefinedItems.desc")))
                                .binding(repelPhantomsWithDefinedItems, () -> repelPhantomsWithDefinedItems, newValue -> repelPhantomsWithDefinedItems = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .group(ListOption.<Item>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.repelPhantomsWithItems.group.repellentItems"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.repelPhantomsWithItems.group.repellentItems.desc")))
                                .binding(phantomRepellentItems, () -> phantomRepellentItems, newList -> phantomRepellentItems = newList)
                                .controller(ItemControllerBuilder::create)
                                .initial(Items.PHANTOM_MEMBRANE)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("cherry-on-top.category.features.enchantmentUpgrading"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.enchantmentUpgrading.enableEnchantmentUpgrading"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.enchantmentUpgrading.enableEnchantmentUpgrading.desc")))
                                .binding(enableEnchantmentUpgrading, () -> enableEnchantmentUpgrading, newValue -> enableEnchantmentUpgrading = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.enchantmentUpgrading.upgradingHasExperienceCost"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.enchantmentUpgrading.upgradingHasExperienceCost.desc")))
                                .binding(upgradingHasExperienceCost, () -> upgradingHasExperienceCost, newValue -> upgradingHasExperienceCost = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.enchantmentUpgrading.upgradingBaseExperienceCost"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.enchantmentUpgrading.upgradingBaseExperienceCost.desc")))
                                .binding(upgradingBaseExperienceCost, () -> upgradingBaseExperienceCost, newValue -> upgradingBaseExperienceCost = newValue)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).min(0))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.enchantmentUpgrading.allowUpgradingSingleEnchantedBooksOnly"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.enchantmentUpgrading.allowUpgradingSingleEnchantedBooksOnly.desc")))
                                .binding(allowUpgradingSingleEnchantedBooksOnly, () -> allowUpgradingSingleEnchantedBooksOnly, newValue -> allowUpgradingSingleEnchantedBooksOnly = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.enchantmentUpgrading.templateLootChance"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.enchantmentUpgrading.templateLootChance.desc")))
                                .binding(templateLootChance, () -> templateLootChance, newValue -> templateLootChance = newValue)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 100).step(1))
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .group(ListOption.<String>createBuilder()
                                .name(Component.translatable("cherry-on-top.features.enchantmentUpgrading.group.templateLootLocations"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.features.enchantmentUpgrading.group.templateLootLocations.desc")))
                                .binding(templateLootLocations, () -> templateLootLocations, newList -> templateLootLocations = newList)
                                .controller(StringControllerBuilder::create)
                                .initial("")
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("cherry-on-top.category.tweaks"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.tweaks.disableDoubleTapSprint"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.tweaks.disableDoubleTapSprint.desc")))
                                .binding(disableDoubleTapSprint, () -> disableDoubleTapSprint, newValue -> disableDoubleTapSprint = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.tweaks.playBowDrawingSounds"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.tweaks.playBowDrawingSounds.desc")))
                                .binding(playBowDrawingSounds, () -> playBowDrawingSounds, newValue -> playBowDrawingSounds = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.tweaks.craftTippedArrowsWithRegularPotions"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.tweaks.craftTippedArrowsWithRegularPotions.desc")))
                                .binding(craftTippedArrowsWithRegularPotions, () -> craftTippedArrowsWithRegularPotions, newValue -> craftTippedArrowsWithRegularPotions = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.tweaks.infinityMendingCompatible"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.tweaks.infinityMendingCompatible.desc")))
                                .binding(infinityMendingCompatible, () -> infinityMendingCompatible, newValue -> infinityMendingCompatible = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.tweaks.hideDebugInfoInSurvival"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.tweaks.hideDebugInfoInSurvival.desc")))
                                .binding(hideDebugInfoInSurvival, () -> hideDebugInfoInSurvival, newValue -> hideDebugInfoInSurvival = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("cherry-on-top.tweaks.riptideWorksOnlyInWater"))
                                .description(OptionDescription.of(Component.translatable("cherry-on-top.tweaks.riptideWorksOnlyInWater.desc")))
                                .binding(riptideWorksOnlyInWater, () -> riptideWorksOnlyInWater, newValue -> riptideWorksOnlyInWater = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .build())
        ).generateScreen(parentScreen);
    }
}
