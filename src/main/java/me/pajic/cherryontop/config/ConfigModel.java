package me.pajic.cherryontop.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

import java.util.List;

@Modmenu(modId = "cherry-on-top")
@Config(name = "cherry-on-top", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    @SectionHeader("features")
    @Nest public EnchantmentUpgrading enchantmentUpgrading = new EnchantmentUpgrading();
    @Nest public PhantomSpawningRework phantomSpawningRework = new PhantomSpawningRework();
    @Nest public MusicDiscLoot musicDiscLoot = new MusicDiscLoot();

    @SectionHeader("tweaks")
    @RestartRequired public boolean craftTippedArrowsWithRegularPotions = false;
    @RestartRequired public boolean infinityMendingCompatible = false;
    public boolean playBowDrawingSounds = false;
    public boolean riptideWorksOnlyInWater = false;
    public boolean hideDebugInfoInSurvival = false;
    @Sync(Option.SyncMode.NONE) public boolean disableDoubleTapSprint = false;

    public static class EnchantmentUpgrading {
        @RestartRequired public boolean enableEnchantmentUpgrading = false;
        public boolean upgradingHasExperienceCost = true;
        @PredicateConstraint("greaterThanZero") public int upgradingBaseExperienceCost = 5;
        public boolean ignoreTooExpensive = false;
        public boolean allowUpgradingSingleEnchantedBooksOnly = false;
        @RestartRequired @RangeConstraint(min = 1, max = 100) public int templateLootChance = 10;
        @RestartRequired public List<String> templateLootLocations = List.of("minecraft:chests/end_city_treasure");

        public static boolean greaterThanZero(int value) {
            return value > 0;
        }
    }

    public static class PhantomSpawningRework {
        public boolean enablePhantomSpawningRework = false;
        @RangeConstraint(min = -64, max = 320) public int phantomSpawnStartHeight = 128;
        @PredicateConstraint("greaterThanZero") public int phantomSpawnFrequencyBase = 30;
        @PredicateConstraint("greaterThanZero") public int phantomSpawnFrequencyRandomOffsetBound = 30;
        public boolean repelPhantomsWithDefinedItems = true;
        public List<String> phantomRepellentItems = List.of("minecraft:phantom_membrane");

        public static boolean greaterThanZero(int value) {
            return value > 0;
        }
    }

    public static class MusicDiscLoot {
        @RestartRequired public boolean enableMusicDiscLoot = false;
        @RestartRequired @RangeConstraint(min = 1, max = 100) public int musicDiscLootChance = 10;
        @RestartRequired public List<String> musicDiscLootLocations = List.of(
                "minecraft:chests/abandoned_mineshaft",
                "minecraft:chests/end_city_treasure",
                "minecraft:chests/igloo_chest",
                "minecraft:chests/jungle_temple",
                "minecraft:chests/pillager_outpost",
                "minecraft:chests/simple_dungeon",
                "minecraft:chests/stronghold_corridor",
                "minecraft:chests/stronghold_crossing",
                "minecraft:chests/stronghold_library",
                "minecraft:chests/woodland_mansion",
                "minecraft:gameplay/cat_morning_gift"
        );
        @RestartRequired public boolean remove13AndCatSimpleDungeonEntries = true;
    }
}
