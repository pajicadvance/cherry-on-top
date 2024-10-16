package me.pajic.cherryontop.mixin.music_control;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.pajic.cherryontop.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;
    @Shadow @Nullable public ClientLevel level;

    @ModifyReturnValue(
            method = "getSituationalMusic",
            at = @At(value = "RETURN", ordinal = 2)
    )
    private Music allowCreativeMusicInSurvival(Music original) {
        if (Main.CONFIG.musicControl.creativeMusicInSurvival()) {
            return player.level().getBiome(player.blockPosition()).value()
                    .getBackgroundMusic()
                    .orElse(Musics.CREATIVE);
        }
        return original;
    }
}
