package me.pajic.cherryontop.mixin.early_loaders.bundles_enabled_by_default;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.config.EarlyLoaderConfig;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FeatureFlags.class)
public class FeatureFlagsMixin {

    @Shadow @Final public static FeatureFlag BUNDLE;

    @Shadow @Final public static FeatureFlag VANILLA;

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/flag/FeatureFlagSet;of(Lnet/minecraft/world/flag/FeatureFlag;)Lnet/minecraft/world/flag/FeatureFlagSet;"
            )
    )
    private static FeatureFlagSet enableBundlesByDefault(FeatureFlagSet original) {
        if (EarlyLoaderConfig.options.enableBundlesByDefault) {
            return FeatureFlagSet.of(VANILLA, BUNDLE);
        }
        return original;
    }
}
