package me.pajic.cherryontop.mixin.double_tap_sprint;

import me.pajic.cherryontop.config.ModConfig;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow
    protected int sprintTriggerTime;

    @Inject(
            method = "aiStep",
            at = @At("HEAD")
    )
    private void onAiStep(CallbackInfo ci) {
        if (ModConfig.disableDoubleTapSprint) {
            sprintTriggerTime = 0;
        }
    }
}
