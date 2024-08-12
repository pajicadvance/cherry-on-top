package me.pajic.cherryontop.mixin.early_loaders.experiments_enabled_by_default;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.config.EarlyLoader;
import net.minecraft.world.level.DataPackConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("unchecked")
@Mixin(DataPackConfig.class)
public class DataPackConfigMixin {

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;"
            )
    )
    private static <E> ImmutableList<E> enableBundlesByDefault(ImmutableList<E> original) {
        if (EarlyLoader.CONFIG.enableBundlesByDefault && EarlyLoader.CONFIG.enableTradeRebalanceByDefault) {
            return (ImmutableList<E>) ImmutableList.of("vanilla", "bundle", "trade_rebalance");
        }
        else if (EarlyLoader.CONFIG.enableBundlesByDefault) {
            return (ImmutableList<E>) ImmutableList.of("vanilla", "bundle");
        }
        else if (EarlyLoader.CONFIG.enableTradeRebalanceByDefault) {
            return (ImmutableList<E>) ImmutableList.of("vanilla", "trade_rebalance");
        }
        return original;
    }
}
