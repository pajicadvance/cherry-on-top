package me.pajic.cherryontop.mixin.early_loaders.stackable_items;

import me.pajic.cherryontop.config.EarlyLoader;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.ThrowablePotionItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrowablePotionItem.class)
public class ThrowablePotionItemMixin extends PotionItem {

    public ThrowablePotionItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "use",
            at = @At("RETURN")
    )
    private void addThrowingCooldown(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (EarlyLoader.CONFIG.splashPotionCooldown > 0 && EarlyLoader.CONFIG.enableStackablePotions) {
            player.getCooldowns().addCooldown(this, EarlyLoader.CONFIG.splashPotionCooldown * 20);
        }
    }
}
