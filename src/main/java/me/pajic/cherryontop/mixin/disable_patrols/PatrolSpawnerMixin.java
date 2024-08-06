package me.pajic.cherryontop.mixin.disable_patrols;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.Main;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"
            )
    )
    private boolean disablePatrols(boolean original) {
        if (Main.CONFIG.disablePillagerPatrols()) {
            return false;
        }
        return original;
    }
}
