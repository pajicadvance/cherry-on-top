package me.pajic.cherryontop.mixin.hide_debug_info_in_survival;

import me.pajic.cherryontop.config.ModConfig;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract boolean isCreative();
    @Shadow public abstract boolean isSpectator();
    @Shadow public abstract void setReducedDebugInfo(boolean reducedDebugInfo);

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void setReducedDebugInfo(CallbackInfo ci) {
        if (ModConfig.hideDebugInfoInSurvival) {
            setReducedDebugInfo(!isCreative() && !isSpectator());
        }
    }
}
