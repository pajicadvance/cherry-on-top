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
    @Nest public Whetstone whetstone = new Whetstone();
    @Nest public PhantomSpawningRework phantomSpawningRework = new PhantomSpawningRework();
    @Nest public AnvilImprovements anvilImprovements = new AnvilImprovements();
    @Nest public BottleOEnchantingImprovements bottleOEnchantingImprovements = new BottleOEnchantingImprovements();
    @Nest public EnchantedBookLootImprovements enchantedBookLootImprovements = new EnchantedBookLootImprovements();
    @Nest public MusicDiscLoot musicDiscLoot = new MusicDiscLoot();
    @Nest public TeleportationPotions teleportationPotions = new TeleportationPotions();

    @SectionHeader("tweaks")
    @RestartRequired public boolean craftTippedArrowsWithRegularPotions = false;
    @RestartRequired public boolean glowstoneDustRecipe = false;
    @RestartRequired public boolean cryingObsidianRecipe = false;
    @RestartRequired public boolean infinityMendingCompatible = false;
    public boolean playBowDrawingSounds = false;
    public boolean disableShulkerDuplication = false;
    @RestartRequired public boolean disableNightVision = false;
    public boolean riptideWorksOnlyInWater = false;
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

    public static class Whetstone {
        @RestartRequired public boolean enableWhetstone = false;
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

    public static class AnvilImprovements {
        public boolean modifyAnvilRepairUnitCosts = false;
        @Nest public Armor armor = new Armor();
        @Nest public Tools tools = new Tools();
        @Nest public UniqueItems uniqueItems = new UniqueItems();
        public boolean modifyDegradationChance = false;
        @RangeConstraint(min = 0.0F, max = 100.0F, decimalPlaces = 1) public float degradationChance = 12.0F;
        public boolean freeUnenchantedRepairs = false;
        public boolean freeRenames = false;
        public boolean noTooExpensive = false;
        public boolean noPriorWorkCost = false;
    }

    public static class Armor {
        public int headArmorUnits = 5;
        public int chestArmorUnits = 8;
        public int legArmorUnits = 7;
        public int footArmorUnits = 4;
    }

    public static class Tools {
        public int pickaxeUnits = 3;
        public int axeUnits = 3;
        public int hoeUnits = 2;
        public int swordUnits = 2;
        public int shovelUnits = 1;
    }

    public static class UniqueItems {
        public int shieldUnits = 6;
        public int elytraUnits = 2;
        public int maceUnits = 2;
        public int whetstoneUnits = 6;
    }

    public static class BottleOEnchantingImprovements {
        @RestartRequired public boolean enableBottleOEnchantingImprovements = false;
        public boolean modifyExperienceReward = true;
        @PredicateConstraint("expressionWithRandValid") public String experienceReward = "30+rand(10)+rand(10)";
        @RestartRequired public boolean renameToExperienceBottle = true;
        @RestartRequired public boolean additionalChestLoot = true;
        @RestartRequired public List<String> bottleLootLocations = List.of(
                "minecraft:chests/abandoned_mineshaft;50;1",
                "minecraft:chests/end_city_treasure;100;1",
                "minecraft:chests/simple_dungeon;100;1",
                "minecraft:chests/stronghold_corridor;100;1",
                "minecraft:chests/stronghold_crossing;100;1",
                "minecraft:chests/stronghold_library;100;2",
                "minecraft:chests/woodland_mansion;100;3"
        );

        public static boolean expressionWithRandValid(String string) {
            return Predicates.expressionWithRandValid(string);
        }
    }

    public static class EnchantedBookLootImprovements {
        @RestartRequired public boolean enableEnchantedBookLootImprovements = false;
        @RestartRequired public boolean additionalChestLoot = true;
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
        public boolean enchantmentLevelWeights = false;
        @PredicateConstraint("positive") public int level1Weight = 16;
        @PredicateConstraint("positive") public int level2Weight = 8;
        @PredicateConstraint("positive") public int level3Weight = 4;
        @PredicateConstraint("positive") public int level4Weight = 2;
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

    public static class TeleportationPotions {
        @RestartRequired public boolean enablePotionOfWormhole = false;
        @RestartRequired public boolean enablePotionOfTeleportation = false;
        public int teleportRadius = 10000;
        public int teleportMaxHeight = 384;
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
