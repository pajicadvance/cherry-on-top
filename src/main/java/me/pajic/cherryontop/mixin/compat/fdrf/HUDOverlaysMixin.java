package me.pajic.cherryontop.mixin.compat.fdrf;

import com.mojang.blaze3d.systems.RenderSystem;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vectorwing.farmersdelight.client.gui.HUDOverlays;

// todo: dirty workaround, find better solution
@IfModLoaded("farmersdelight")
@Mixin(HUDOverlays.class)
public class HUDOverlaysMixin {

    @Redirect(
            method = "drawNourishmentOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"
            )
    )
    private static void disableDepthTest1() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
    }

    @Redirect(
            method = "drawComfortOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V"
            )
    )
    private static void disableDepthTest2() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
    }

    @Redirect(
            method = "drawNourishmentOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"
            )
    )
    private static void enableDepthTest1() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }

    @Redirect(
            method = "drawComfortOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;disableBlend()V"
            )
    )
    private static void enableDepthTest2() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
    }
}
