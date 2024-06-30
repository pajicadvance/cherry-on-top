package me.pajic.cherryontop.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.minecraft.util.RandomSource;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

import java.util.List;

@Modmenu(modId = "cherry-on-top")
@Config(name = "cherry-on-top", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    @SectionHeader("features")
    @Nest public EnchantmentUpgrading enchantmentUpgrading = new EnchantmentUpgrading();
    @Nest public EnchantmentDisabler enchantmentDisabler = new EnchantmentDisabler();
    @Nest public PhantomSpawningRework phantomSpawningRework = new PhantomSpawningRework();
    @Nest public MusicDiscLoot musicDiscLoot = new MusicDiscLoot();
    @Nest public BottleOEnchantingImprovements bottleOEnchantingImprovements = new BottleOEnchantingImprovements();

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
        @RestartRequired @RangeConstraint(min = 1, max = 100) public int templateLootChance = 10;
        @RestartRequired public List<String> templateLootLocations = List.of("minecraft:chests/end_city_treasure");

        public static boolean greaterThanZero(int value) {
            return Predicates.greaterThanZero(value);
        }
    }

    public static class EnchantmentDisabler {
        @RestartRequired public boolean enableEnchantmentDisabler = false;
        @RestartRequired public List<String> disabledEnchantments = List.of("minecraft:mending");
    }

    public static class PhantomSpawningRework {
        public boolean enablePhantomSpawningRework = false;
        @RangeConstraint(min = -64, max = 320) public int phantomSpawnStartHeight = 128;
        @PredicateConstraint("expressionWithRandValid") public String phantomSpawnFrequency = "30+rand(30)";
        public boolean repelPhantomsWithDefinedItems = true;
        public List<String> phantomRepellentItems = List.of("minecraft:phantom_membrane");

        public static boolean expressionWithRandValid(String string) {
            return Predicates.expressionWithRandValid(string);
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

    public static class BottleOEnchantingImprovements {
        @RestartRequired public boolean enableBottleOEnchantingImprovements = false;
        public boolean modifyExperienceReward = true;
        @PredicateConstraint("expressionWithRandValid") public String experienceReward = "30+rand(10)+rand(10)";
        @RestartRequired public boolean renameToExperienceBottle = true;
        @RestartRequired public boolean addToLootChests = true;
        @RestartRequired @RangeConstraint(min = 1, max = 100) public int bottleLootChance = 50;
        @RestartRequired public List<String> bottleLootLocations = List.of(
                "minecraft:chests/abandoned_mineshaft",
                "minecraft:chests/pillager_outpost;2"
        );

        public static boolean expressionWithRandValid(String string) {
            return Predicates.expressionWithRandValid(string);
        }
    }

    public static class Predicates {

        public static boolean greaterThanZero(int value) {
            return value > 0;
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
