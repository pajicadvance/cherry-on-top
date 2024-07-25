package me.pajic.cherryontop.item;

import me.pajic.cherryontop.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PotionOfTeleportationItem extends PotionItem {
    public PotionOfTeleportationItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (!level.isClientSide) {
            int radius = Main.CONFIG.potionOfTeleportation.teleportRadius();
            int maxHeight = Main.CONFIG.potionOfTeleportation.teleportMaxHeight();
            int levelHeight = player.level().getHeight();
            if (levelHeight > maxHeight) levelHeight = maxHeight;
            RandomSource random = player.getRandom();
            int x, y, z;

            do {
                x = random.nextIntBetweenInclusive((int) (player.getX() - radius), (int) (player.getX() + radius));
                y = random.nextInt(levelHeight);
                z = random.nextInt((int) (player.getZ() - radius), (int) (player.getZ() + radius));
            } while (!player.randomTeleport(x, y, z, false));
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.consume(1, player);
        }

        if (player == null || !player.hasInfiniteMaterials()) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(
                Component.translatable("item.cherry-on-top.potion_of_teleportation.tooltip")
                        .withStyle(ChatFormatting.BLUE)
        );
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        return InteractionResult.PASS;
    }

    @Override
    public boolean isEnabled(@NotNull FeatureFlagSet enabledFeatures) {
        return Main.CONFIG.potionOfTeleportation.enablePotionOfTeleportation();
    }
}
