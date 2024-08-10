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

public class EarlyLoaderConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger("CherryOnTop-EarlyLoader");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("cherry-on-top-early.json");
    public static Options options;

    public static void loadConfig() {
        try (FileReader reader = new FileReader(configFile.toFile())) {
            options = GSON.fromJson(reader, Options.class);
        } catch (FileNotFoundException | JsonSyntaxException e) {
            LOGGER.warn("Config doesn't exist or is malformed, initializing new mod config...");
            initializeConfig();
        } catch (IOException e) {
            LOGGER.error("Failed to read mod config", e);
        }
    }

    private static void initializeConfig() {
        try (FileWriter writer = new FileWriter(configFile.toFile())) {
            options = new Options();
            GSON.toJson(options, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save mod config", e);
        }
    }

    public static class Options {
        public boolean enableBundlesByDefault = true;
        public boolean enableStackablePotions = true;
        public int potionMaxStackSize = 3;
        Options() {}
    }
}
