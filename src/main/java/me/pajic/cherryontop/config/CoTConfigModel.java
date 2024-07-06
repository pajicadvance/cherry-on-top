package me.pajic.cherryontop.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.minecraft.util.RandomSource;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.util.List;

@Modmenu(modId = "cherry-on-top")
@Config(name = "cherry-on-top", wrapperName = "CoTConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class CoTConfigModel {
    @SectionHeader("features")
    @Nest public EnchantmentUpgrading enchantmentUpgrading = new EnchantmentUpgrading();
    @Nest public EnchantmentDisabler enchantmentDisabler = new EnchantmentDisabler();
    @Nest public PhantomSpawningRework phantomSpawningRework = new PhantomSpawningRework();
    @Nest public BottleOEnchantingImprovements bottleOEnchantingImprovements = new BottleOEnchantingImprovements();
    @Nest public EnchantedBookLootImprovements enchantedBookLootImprovements = new EnchantedBookLootImprovements();
    @Nest public MusicDiscLoot musicDiscLoot = new MusicDiscLoot();

    @SectionHeader("tweaks")
    @RestartRequired public boolean craftTippedArrowsWithRegularPotions = false;
    @RestartRequired public boolean infinityMendingCompatible = false;
    public boolean playBowDrawingSounds = false;
    public boolean riptideWorksOnlyInWater = false;
    public boolean disableEnchantingTable = false;
    public boolean hideDebugInfoInSurvival = false;
    @Sync(Option.SyncMode.NONE) public boolean disableDoubleTapSprint = false;

    public static class EnchantmentUpgrading {
        @RestartRequired public boolean enableEnchantmentUpgrading = false;
        public boolean upgradingHasExperienceCost = true;
        @PredicateConstraint("greaterThanZero") public int upgradingBaseExperienceCost = 5;
        public boolean ignoreTooExpensive = false;
        public boolean allowUpgradingSingleEnchantedBooksOnly = false;
        @RestartRequired public List<String> templateLootLocations = List.of(
                "minecraft:chests/end_city_treasure;10;1"
        );

        public static boolean greaterThanZero(int value) {
            return Predicates.greaterThanZero(value);
        }
    }

    public static class EnchantmentDisabler {
        @RestartRequired public boolean enableEnchantmentDisabler = false;
        @RestartRequired public List<String> disabledEnchantments = List.of(
                "minecraft:mending"
        );
    }

    public static class PhantomSpawningRework {
        public boolean enablePhantomSpawningRework = false;
        @RangeConstraint(min = -64, max = 320) public int phantomSpawnStartHeight = 128;
        @PredicateConstraint("expressionWithRandValid") public String phantomSpawnFrequency = "30+rand(30)";
        public boolean repelPhantomsWithDefinedItems = true;
        public List<String> phantomRepellentItems = List.of(
                "minecraft:phantom_membrane"
        );

        public static boolean expressionWithRandValid(String string) {
            return Predicates.expressionWithRandValid(string);
        }
    }

    public static class BottleOEnchantingImprovements {
        @RestartRequired public boolean enableBottleOEnchantingImprovements = false;
        public boolean modifyExperienceReward = true;
        @PredicateConstraint("expressionWithRandValid") public String experienceReward = "30+rand(10)+rand(10)";
        @RestartRequired public boolean renameToExperienceBottle = true;
        @RestartRequired public boolean additionalChestLoot = true;
        @RestartRequired public List<String> bottleLootLocations = List.of(
                "minecraft:chests/abandoned_mineshaft;50;1",
                "minecraft:chests/pillager_outpost;100;2"
        );

        public static boolean expressionWithRandValid(String string) {
            return Predicates.expressionWithRandValid(string);
        }
    }

    public static class EnchantedBookLootImprovements {
        @RestartRequired public boolean enableEnchantedBookLootImprovements = false;
        @RestartRequired public boolean additionalChestLoot = false;
        @RestartRequired public List<String> bookLootLocations = List.of(
                "minecraft:chests/abandoned_mineshaft;10;1",
                "minecraft:chests/ancient_city;10;1",
                "minecraft:chests/end_city_treasure;10;1",
                "minecraft:chests/jungle_temple;10;1",
                "minecraft:chests/pillager_outpost;10;1",
                "minecraft:chests/simple_dungeon;10;1",
                "minecraft:chests/stronghold_corridor;10;1",
                "minecraft:chests/stronghold_crossing;10;1",
                "minecraft:chests/stronghold_library;10;1",
                "minecraft:chests/woodland_mansion;10;1"
        );
        @RestartRequired public boolean structureSpecificLoot = false;
        @RestartRequired public List<String> enchantmentStructureMap = List.of(
                "minecraft:frost_walker,minecraft:chests/ancient_city_ice_box;100;1"
        );
        public boolean modifyMaxEnchantmentLevel = false;
        public List<String> maxLevels = List.of(
                "minecraft:sharpness/5"
        );
        public boolean enchantmentLevelWeights = false;
        @PredicateConstraint("positive") public int level1Weight = 1;
        @PredicateConstraint("positive") public int level2Weight = 1;
        @PredicateConstraint("positive") public int level3Weight = 1;
        @PredicateConstraint("positive") public int level4Weight = 1;
        @PredicateConstraint("positive") public int level5Weight = 1;

        public static boolean positive(int value) {
            return Predicates.positive(value);
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
                "minecraft:chests/woodland_mansion"
        );
        @RestartRequired public boolean remove13AndCatSimpleDungeonEntries = true;
    }

    public static class Predicates {

        public static boolean greaterThanZero(int value) {
            return value > 0;
        }

        public static boolean positive(int value) {
            return value >= 0;
        }

        public static boolean expressionWithRandValid(String string) {
            Function rand = new Function("rand", 1) {
                @Override
                public double apply(double... args) {
                    return 1 + RandomSource.create().nextInt((int) args[0]);
                }
            };
            try {
                Expression expression = new ExpressionBuilder(string)
                        .function(rand)
                        .build();
                if (expression.validate().isValid() && (int)expression.evaluate() > 0) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }
    }
}
