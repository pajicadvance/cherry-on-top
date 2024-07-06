package me.pajic.cherryontop.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.pajic.cherryontop.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CoTEnchantmentUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger("CoT-EnchantmentUtil");

    public static int getNewMaxLevel(Holder<Enchantment> enchantmentHolder, RegistryAccess registryAccess) {
        for (String configEntry : Main.CONFIG.enchantedBookLootImprovements.maxLevels()) {
            String[] enchantmentLevelPair = configEntry.split("/");

            if (enchantmentLevelPair.length == 2) {
                Optional<Holder.Reference<Enchantment>> enchantment =
                        registryAccess.registryOrThrow(Registries.ENCHANTMENT).getHolder(ResourceLocation.parse(enchantmentLevelPair[0]));

                if (enchantment.isPresent()) {
                    if (enchantment.get().is(enchantmentHolder)) {
                        int newMaxLevel;

                        try {
                            newMaxLevel = Integer.parseInt(enchantmentLevelPair[1]);
                            if (newMaxLevel > enchantmentHolder.value().getMaxLevel()) {
                                LOGGER.warn("Max enchantment level can't be higher than the vanilla maximum, ignoring entry {}", configEntry);
                            }
                            else if (newMaxLevel > 0) {
                                return newMaxLevel;
                            }
                            else {
                                LOGGER.warn("Max enchantment level must be higher than zero, ignoring entry {}", configEntry);
                            }
                        } catch (NumberFormatException e) {
                            LOGGER.warn("Enchantment level in entry {} is invalid, ignoring", configEntry);
                        }
                    }
                }
                else {
                    LOGGER.warn("Enchantment {} in entry {} not found, ignoring", enchantmentLevelPair[0], configEntry);
                }
            }
            else {
                LOGGER.warn("Entry {} does not have all required values, ignoring", configEntry);
            }
        }
        return enchantmentHolder.value().getMaxLevel();
    }

    public static int calculateNewLevelFromWeights(Holder<Enchantment> enchantmentHolder, RandomSource randomSource, RegistryAccess registryAccess) {
        int maxLevel = enchantmentHolder.value().getMaxLevel();

        if (Main.CONFIG.enchantedBookLootImprovements.modifyMaxEnchantmentLevel()) {
            maxLevel = getNewMaxLevel(enchantmentHolder, registryAccess);
        }

        if (maxLevel == 1) {
            return 1;
        }

        int[] weights = new int[] {
                Main.CONFIG.enchantedBookLootImprovements.level1Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level2Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level3Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level4Weight(),
                Main.CONFIG.enchantedBookLootImprovements.level5Weight()
        };

        IntList pool = new IntArrayList();
        for (int i = 0; i < maxLevel; i++) {
            int j = weights[i];
            for (int k = 0; k < j; k++) {
                pool.add(i + 1);
            }
        }

        return pool.getInt(randomSource.nextInt(pool.size()));
    }
}
