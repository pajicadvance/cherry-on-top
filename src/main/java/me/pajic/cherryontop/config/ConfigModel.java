package me.pajic.cherryontop.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

import java.util.List;

@Modmenu(modId = "cherry-on-top")
@Config(name = "cherry-on-top", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    @SectionHeader("enchantmentUpgrading")
    @RestartRequired public boolean enableEnchantmentUpgrading = false;
    @Nest public EnchantmentUpgradingOptions enchantmentUpgradingOptions = new EnchantmentUpgradingOptions();

    @SectionHeader("phantomSpawningRework")
    public boolean enablePhantomSpawningRework = false;
    @Nest public PhantomSpawningReworkOptions phantomSpawningReworkOptions = new PhantomSpawningReworkOptions();

    @SectionHeader("tweaks")
    @RestartRequired public boolean craftTippedArrowsWithRegularPotions = false;
    @RestartRequired public boolean infinityMendingCompatible = false;
    public boolean riptideWorksOnlyInWater = false;
    public boolean hideDebugInfoInSurvival = false;
    public boolean playBowDrawingSounds = false;
    @Sync(Option.SyncMode.NONE) public boolean disableDoubleTapSprint = false;

    public static class EnchantmentUpgradingOptions {
        public boolean upgradingHasExperienceCost = true;
        @PredicateConstraint("greaterThanZero") public int upgradingBaseExperienceCost = 5;
        public boolean allowUpgradingSingleEnchantedBooksOnly = false;
        @RestartRequired @RangeConstraint(min = 1, max = 100) public int templateLootChance = 10;
        @RestartRequired public List<String> templateLootLocations = List.of("minecraft:chests/end_city_treasure");

        public static boolean greaterThanZero(int value) {
            return value > 0;
        }
    }

    public static class PhantomSpawningReworkOptions {
        @RangeConstraint(min = -64, max = 320) public int phantomSpawnStartHeight = 128;
        @PredicateConstraint("greaterThanZero") public int phantomSpawnFrequencyBase = 30;
        @PredicateConstraint("greaterThanZero") public int phantomSpawnFrequencyRandomOffsetBound = 30;
        public boolean repelPhantomsWithDefinedItems = false;
        public List<String> phantomRepellentItems = List.of("minecraft:phantom_membrane");

        public static boolean greaterThanZero(int value) {
            return value > 0;
        }
    }
}
