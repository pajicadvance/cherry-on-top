package me.pajic.cherryontop.item;

import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.util.CoTUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EnderBackpackItem extends Item {
    public EnderBackpackItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (level.isClientSide()) {
            level.playSound(player, player.getOnPos(), SoundEvents.ENDER_CHEST_OPEN, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (!level.isClientSide()) {
            CoTUtil.openEnderBackpack(player);
        }
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public boolean isEnabled(@NotNull FeatureFlagSet enabledFeatures) {
        return Main.CONFIG.enderBackpack.enableEnderBackpack();
    }
}
