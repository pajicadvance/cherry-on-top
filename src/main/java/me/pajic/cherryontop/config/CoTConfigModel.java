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
    @Nest public EnderBackpack enderBackpack = new EnderBackpack();
    @Nest public TeleportationPotions teleportationPotions = new TeleportationPotions();
    @Nest public InfoOverlays infoOverlays = new InfoOverlays();
    @Nest public PhantomSpawningRework phantomSpawningRework = new PhantomSpawningRework();
    @Nest public DeathImprovements deathImprovements = new DeathImprovements();
    @Nest public AnvilImprovements anvilImprovements = new AnvilImprovements();
    @Nest public BottleOEnchantingImprovements bottleOEnchantingImprovements = new BottleOEnchantingImprovements();
    @Nest public EnchantedBookLootImprovements enchantedBookLootImprovements = new EnchantedBookLootImprovements();
    @Nest public MusicDiscLoot musicDiscLoot = new MusicDiscLoot();
    @Nest public MusicControl musicControl = new MusicControl();

    @SectionHeader("tweaks")
    @RestartRequired public boolean glowstoneDustRecipe = true;
    @RestartRequired public boolean cryingObsidianRecipe = true;
    @RestartRequired public boolean soulSpeedNoDamage = true;
    public boolean creeperExplosionDropsAll = true;
    public boolean disableShulkerDuplication = false;
    public boolean disablePillagerPatrols = false;
    @RestartRequired public boolean disableNightVision = false;
    public boolean riptideWorksOnlyInWater = false;
    public boolean hideDebugInfoInSurvival = false;
    @Sync(Option.SyncMode.NONE) public boolean disableDoubleTapSprint = false;

    public static class EnchantmentUpgrading {
        @RestartRequired public boolean enableEnchantmentUpgrading = true;
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
        @RestartRequired public boolean enableWhetstone = true;
    }

    public static class EnderBackpack {
        @RestartRequired public boolean enableEnderBackpack = true;
    }

    public static class TeleportationPotions {
        @RestartRequired public boolean enablePotionOfWormhole = true;
        @RestartRequired public boolean enablePotionOfTeleportation = true;
        public int teleportRadius = 10000;
        public int teleportMaxHeight = 384;
    }

    public static class InfoOverlays {
        public boolean enableCompassOverlay = true;
        public boolean enableClockOverlay = true;
        @Sync(Option.SyncMode.NONE) public boolean hideIfEmiOpen = false;
        @Sync(Option.SyncMode.NONE) public boolean textBackground = true;
        @Sync(Option.SyncMode.NONE) @RangeConstraint(min = 0.0F, max = 1.0F) public float textBackgroundOpacity = 0.3F;
        @Sync(Option.SyncMode.NONE) public boolean textShadow = false;
        @Sync(Option.SyncMode.NONE) public boolean coloredSeason = false;
        @Sync(Option.SyncMode.NONE) public boolean coloredWeather = false;
    }

    public static class PhantomSpawningRework {
        public boolean enablePhantomSpawningRework = true;
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

    public static class DeathImprovements {
        public boolean noItemSplatterOnDeath = true;
        public boolean noDeathItemDespawn = true;
        public boolean playerDropMoreXpOnDeath = true;
        @RangeConstraint(min = 0, max = 100) public int droppedExperiencePercent = 80;
    }

    public static class AnvilImprovements {
        public boolean modifyAnvilRepairUnitCosts = true;
        @Nest public Armor armor = new Armor();
        @Nest public Tools tools = new Tools();
        @Nest public UniqueItems uniqueItems = new UniqueItems();
        public List<String> modTags = List.of("farmersdelight:tools/knives;1");
        public List<String> modItems = List.of();
        public boolean modifyDegradationChance = true;
        @RangeConstraint(min = 0.0F, max = 100.0F, decimalPlaces = 1) public float degradationChance = 6.0F;
        public boolean freeUnenchantedRepairs = true;
        public boolean freeRenames = true;
        public boolean noTooExpensive = true;
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
        @RestartRequired public boolean enableBottleOEnchantingImprovements = true;
        public boolean modifyExperienceReward = true;
        @PredicateConstraint("expressionWithRandValid") public String experienceReward = "30+rand(10)+rand(10)";
        @RestartRequired @Sync(Option.SyncMode.NONE) public boolean renameToExperienceBottle = true;
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
        @RestartRequired public boolean enableEnchantedBookLootImprovements = true;
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
                "minecraft:respiration;10,minecraft:depth_strider;10,minecraft:aqua_affinity;10!minecraft:chests/underwater_ruin_small;50,minecraft:chests/underwater_ruin_big;100,minecraft:chests/buried_treasure;100"
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
        @RestartRequired public boolean enableMusicDiscLoot = true;
        @RestartRequired public List<String> musicDiscLootLocations = List.of(
                "minecraft:chests/abandoned_mineshaft;10",
                "minecraft:chests/end_city_treasure;10",
                "minecraft:chests/igloo_chest;10",
                "minecraft:chests/jungle_temple;10",
                "minecraft:chests/pillager_outpost;10",
                "minecraft:chests/simple_dungeon;10",
                "minecraft:chests/stronghold_corridor;10",
                "minecraft:chests/stronghold_crossing;10",
                "minecraft:chests/stronghold_library;10",
                "minecraft:chests/woodland_mansion;10",
                "minecraft:gameplay/cat_morning_gift;10"
        );
        @RestartRequired public boolean remove13AndCatLootEntries = true;
    }

    public static class MusicControl {
        public boolean enableNextTrackKeybind = false;
        public boolean stopMusicOnJukeboxUse = false;
        public boolean creativeMusicInSurvival = false;
        public boolean modifyMusicDelays = false;
        public int musicMinDelay = 600;
        public int musicMaxDelay = 1200;
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
