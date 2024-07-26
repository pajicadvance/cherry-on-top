package me.pajic.cherryontop.item;

import me.pajic.cherryontop.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PotionOfTeleportationItem extends CustomPotionItem {

    public PotionOfTeleportationItem(Properties properties, Component tooltip, boolean enabledFlag) {
        super(properties, tooltip, enabledFlag);
    }

    @Override
    public void runCustomBehavior(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            int radius = Main.CONFIG.teleportationPotions.teleportRadius();
            int maxHeight = Main.CONFIG.teleportationPotions.teleportMaxHeight();
            int levelHeight = player.level().getHeight();
            if (levelHeight > maxHeight) levelHeight = maxHeight;
            RandomSource random = player.getRandom();
            int x, y, z;

            do {
                x = random.nextIntBetweenInclusive((int) (player.getX() - radius), (int) (player.getX() + radius));
                y = random.nextInt(levelHeight);
                z = random.nextIntBetweenInclusive((int) (player.getZ() - radius), (int) (player.getZ() + radius));
            } while (!player.randomTeleport(x, y, z, false));
        }
    }
}
