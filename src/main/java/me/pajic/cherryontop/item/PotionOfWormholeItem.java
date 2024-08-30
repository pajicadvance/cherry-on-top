package me.pajic.cherryontop.item;

import me.pajic.cherryontop.gui.WormholeScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PotionOfWormholeItem extends CustomPotionItem {

    public PotionOfWormholeItem(Properties properties, Component tooltip, boolean enabledFlag) {
        super(properties, tooltip, enabledFlag);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void runCustomBehavior(ItemStack stack, Level level, Player player) {
        if (level.isClientSide && level.players().size() > 1) {
            Minecraft.getInstance().setScreen(new WormholeScreen(player));
        }
    }
}
