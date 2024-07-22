package me.pajic.cherryontop.mixin.disable_shulker_duplication;

import me.pajic.cherryontop.Main;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Shulker.class)
public class ShulkerMixin {

    @Inject(method = "hitByShulkerBullet", at = @At("HEAD"), cancellable = true)
    private void disableShulkerDuplication(CallbackInfo ci) {
        if (Main.CONFIG.disableShulkerDuplication()) {
            ci.cancel();
        }
    }
}
