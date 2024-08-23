package me.pajic.cherryontop.mixin.death_improvements;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.data.CoTData;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;discard()V",
                    ordinal = 1
            )
    )
    private boolean noDespawnIfNoDespawnComponentPresent(ItemEntity instance) {
        if (Main.CONFIG.deathImprovements.noDeathItemDespawn()) {
            return !instance.getItem().has(CoTData.NO_DESPAWN);
        }
        return true;
    }
}
