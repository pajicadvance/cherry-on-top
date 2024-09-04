package me.pajic.cherryontop.config;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class EarlyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger("CherryOnTop-EarlyLoader");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("cherry-on-top-early.json");
    public static Config CONFIG;

    public static class Config {
        public boolean enableBundlesByDefault = true;
        public boolean enableTradeRebalanceByDefault = true;
        public boolean enableFasterObsidianMining = true;
        public boolean enableStackablePotions = true;
        public int potionMaxStackSize = 3;
        public int splashPotionCooldown = 1;
        public boolean enableStackableStews = true;
        public int stewMaxStackSize = 64;
        public boolean enableStackableEnchantedBooks = true;
        public int enchantedBookMaxStackSize = 64;
        public boolean enableStackableHorseArmor = true;
        public int horseArmorMaxStackSize = 64;
        public boolean enableStackableMusicDiscs = true;
        public int musicDiscMaxStackSize = 64;
        public boolean enableStackableSaddles = true;
        public int saddleMaxStackSize = 64;
        public boolean enableStackableMinecarts = true;
        public int minecartMaxStackSize = 16;
        public boolean enableStackableBoats = true;
        public int boatMaxStackSize = 16;
        public boolean enableStackableCakes = true;
        public int cakeMaxStackSize = 16;
        public boolean enableStackableBeds = true;
        public int bedMaxStackSize = 16;
        public boolean fd_enableStackableBowlFoods = true;
        public int fd_bowlFoodMaxStackSize = 64;
        public boolean fd_enableStackableDrinks = true;
        public int fd_drinkMaxStackSize = 64;
    }

    public static void loadConfig() {
        readConfig();
        validateConfig();
        saveConfig();
    }

    private static void readConfig() {
        try (FileReader reader = new FileReader(configFile.toFile())) {
            CONFIG = GSON.fromJson(reader, Config.class);
        } catch (FileNotFoundException | JsonSyntaxException e) {
            LOGGER.warn("Config doesn't exist or is malformed, initializing new mod config...");
            initializeConfig();
        } catch (IOException e) {
            LOGGER.error("Failed to read mod config", e);
        }
    }

    private static void initializeConfig() {
        try (FileWriter writer = new FileWriter(configFile.toFile())) {
            CONFIG = new Config();
            GSON.toJson(CONFIG, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to initialize mod config", e);
        }
    }

    private static void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile.toFile())) {
            GSON.toJson(CONFIG, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save mod config", e);
        }
    }

    private static void validateConfig() {
        if (CONFIG.splashPotionCooldown < 0) {
            LOGGER.warn("Invalid value for 'splashPotionCooldown', allowed range: 0-inf. Using default value of 1");
            CONFIG.splashPotionCooldown = 1;
        }
        if (CONFIG.potionMaxStackSize < 1 || CONFIG.potionMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'potionMaxStackSize', allowed range: 1-64. Using default value of 3");
            CONFIG.potionMaxStackSize = 3;
        }
        if (CONFIG.stewMaxStackSize < 1 || CONFIG.stewMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'stewMaxStackSize', allowed range: 1-64. Using default value of 64");
            CONFIG.stewMaxStackSize = 64;
        }
        if (CONFIG.enchantedBookMaxStackSize < 1 || CONFIG.enchantedBookMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'enchantedBookMaxStackSize', allowed range: 1-64. Using default value of 64");
            CONFIG.enchantedBookMaxStackSize = 64;
        }
        if (CONFIG.horseArmorMaxStackSize < 1 || CONFIG.horseArmorMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'horseArmorMaxStackSize', allowed range: 1-64. Using default value of 64");
            CONFIG.horseArmorMaxStackSize = 64;
        }
        if (CONFIG.musicDiscMaxStackSize < 1 || CONFIG.musicDiscMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'musicDiscMaxStackSize', allowed range: 1-64. Using default value of 64");
            CONFIG.musicDiscMaxStackSize = 64;
        }
        if (CONFIG.saddleMaxStackSize < 1 || CONFIG.saddleMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'saddleMaxStackSize', allowed range: 1-64. Using default value of 64");
            CONFIG.saddleMaxStackSize = 64;
        }
        if (CONFIG.minecartMaxStackSize < 1 || CONFIG.minecartMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'minecartMaxStackSize', allowed range: 1-64. Using default value of 16");
            CONFIG.minecartMaxStackSize = 16;
        }
        if (CONFIG.boatMaxStackSize < 1 || CONFIG.boatMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'boatMaxStackSize', allowed range: 1-64. Using default value of 16");
            CONFIG.boatMaxStackSize = 16;
        }
        if (CONFIG.cakeMaxStackSize < 1 || CONFIG.cakeMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'cakeMaxStackSize', allowed range: 1-64. Using default value of 16");
            CONFIG.cakeMaxStackSize = 16;
        }
        if (CONFIG.bedMaxStackSize < 1 || CONFIG.bedMaxStackSize > 64) {
            LOGGER.warn("Invalid value for 'bedMaxStackSize', allowed range: 1-64. Using default value of 16");
            CONFIG.bedMaxStackSize = 16;
        }
    }
}
