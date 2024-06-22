package me.pajic.cherryontop.mixin.tipped_arrows_from_regular_potions.emi;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.emi.emi.VanillaPlugin;
import me.pajic.cherryontop.config.ModConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@IfModLoaded("emi")
@Mixin(VanillaPlugin.class)
public class VanillaPluginMixin {

    @ModifyArg(
            method = "lambda$register$12",
            at = @At(
                    value = "INVOKE",
                    target = "Ldev/emi/emi/EmiPort;setPotion(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/alchemy/Potion;)Lnet/minecraft/world/item/ItemStack;",
                    ordinal = 0
            )
    )
    private static ItemStack setRequiredPotionType(ItemStack stack) {
        if (ModConfig.craftTippedArrowsWithRegularPotions) {
            return new ItemStack(Items.POTION);
        }
        return stack;
    }
}
