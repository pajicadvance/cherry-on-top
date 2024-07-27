package me.pajic.cherryontop.mixin.disable_night_vision;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.cherryontop.Main;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {

    @WrapWithCondition(
            method = "addVanillaMixes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Builder;addMix(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)V",
                    ordinal = 3
            )
    )
    private static boolean removeNightVisionRecipe(PotionBrewing.Builder instance, Holder<Potion> input, Item reagent, Holder<Potion> result) {
        return !Main.CONFIG.disableNightVision();
    }

    @WrapWithCondition(
            method = "addVanillaMixes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Builder;addMix(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)V",
                    ordinal = 4
            )
    )
    private static boolean removeExtendedNightVisionRecipe(PotionBrewing.Builder instance, Holder<Potion> input, Item reagent, Holder<Potion> result) {
        return !Main.CONFIG.disableNightVision();
    }

    @WrapWithCondition(
            method = "addVanillaMixes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Builder;addMix(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)V",
                    ordinal = 5
            )
    )
    private static boolean removeInvisibilityRecipe(PotionBrewing.Builder instance, Holder<Potion> input, Item reagent, Holder<Potion> result) {
        return !Main.CONFIG.disableNightVision();
    }

    @WrapWithCondition(
            method = "addVanillaMixes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Builder;addMix(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)V",
                    ordinal = 6
            )
    )
    private static boolean removeExtendedNightVisionToExtendedInvisibilityRecipe(PotionBrewing.Builder instance, Holder<Potion> input, Item reagent, Holder<Potion> result) {
        return !Main.CONFIG.disableNightVision();
    }

    @ModifyReceiver(
            method = "addVanillaMixes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/alchemy/PotionBrewing$Builder;addMix(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/Item;Lnet/minecraft/core/Holder;)V",
                    ordinal = 7
            )
    )
    private static PotionBrewing.Builder addNewInvisibilityRecipe(PotionBrewing.Builder instance, Holder<Potion> input, Item reagent, Holder<Potion> result) {
        if (Main.CONFIG.disableNightVision()) {
            instance.addMix(Potions.AWKWARD, Items.GOLDEN_CARROT, Potions.INVISIBILITY);
        }
        return instance;
    }
}
