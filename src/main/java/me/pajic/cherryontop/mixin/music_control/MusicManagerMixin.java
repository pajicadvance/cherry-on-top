package me.pajic.cherryontop.mixin.music_control;

import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public class MusicManagerMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void stopMusicOnJukeboxUse(CallbackInfo ci) {

    }
}
