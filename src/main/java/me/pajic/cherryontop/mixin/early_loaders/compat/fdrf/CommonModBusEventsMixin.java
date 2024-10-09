package me.pajic.cherryontop.mixin.early_loaders.compat.fdrf;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.event.CommonModBusEvents;

@IfModLoaded("farmersdelight")
@Mixin(value = CommonModBusEvents.class, remap = false)
public class CommonModBusEventsMixin {

    @ModifyExpressionValue(
            method = "onModifyDefaultComponents",
            at = @At(
                    value = "INVOKE",
                    target = "Lio/github/fabricators_of_create/porting_lib/config/ModConfigSpec$BooleanValue;get()Ljava/lang/Object;",
                    ordinal = 0
            )
    )
    private static Object onModifyDefaultComponents(Object original) {
        return false;
    }
}
