package me.pajic.cherryontop.util;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.pajic.cherryontop.Main;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.enchantment.Enchantment;

public class CoTEnchantmentUtil {

    public static int calculateNewLevelFromWeights(Holder<Enchantment> enchantmentHolder, RandomSource randomSource) {
        int maxLevel = enchantmentHolder.value().getMaxLevel();

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
