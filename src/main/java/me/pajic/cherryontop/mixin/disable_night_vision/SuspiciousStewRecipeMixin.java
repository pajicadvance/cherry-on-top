package me.pajic.cherryontop.mixin.disable_night_vision;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.cherryontop.Main;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.SuspiciousStewRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(SuspiciousStewRecipe.class)
public class SuspiciousStewRecipeMixin {

    @ModifyReturnValue(
            method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("RETURN")
    )
    private ItemStack replaceNightVisionWithInvisibility(ItemStack original) {
        if (Main.CONFIG.disableNightVision() &&
                original.get(DataComponents.SUSPICIOUS_STEW_EFFECTS).effects().stream().anyMatch(entry ->
                        entry.effect().is(MobEffects.NIGHT_VISION.unwrapKey().get())
                )
        ) {
            ItemStack modified = original.copy();
            modified.set(
                    DataComponents.SUSPICIOUS_STEW_EFFECTS,
                    new SuspiciousStewEffects(List.of(new SuspiciousStewEffects.Entry(MobEffects.INVISIBILITY, 100)))
            );
            return modified;
        }
        return original;
    }
}
