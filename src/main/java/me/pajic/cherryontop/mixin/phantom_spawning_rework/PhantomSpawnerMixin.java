package me.pajic.cherryontop.mixin.phantom_spawning_rework;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {

    @Redirect(
            method = "tick",
            slice = @Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/server/level/ServerLevel;random:Lnet/minecraft/util/RandomSource;",
                            opcode = Opcodes.GETFIELD
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/server/level/ServerLevel;getSkyDarken()I"
                    )
            ),
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/levelgen/PhantomSpawner;nextTick:I",
                    opcode = Opcodes.PUTFIELD,
                    ordinal = 1
            )
    )
    private void modifySpawnCheckFrequency(PhantomSpawner instance, int value, @Local RandomSource randomSource) {
        if (ModConfig.enablePhantomSpawningRework) {
            instance.nextTick += (ModConfig.phantomSpawnFrequencyBase + randomSource.nextInt(ModConfig.phantomSpawnFrequencyRandomOffsetBound)) * 20;
        }
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;isSpectator()Z"
            )
    )
    private boolean repelIfHoldingRepellentItem(boolean original, @Local ServerPlayer serverPlayer) {
        if (original || !ModConfig.phantomRepellentItems.isEmpty()) {
            return true;
        }
        return ModConfig.phantomRepellentItems.contains(serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem()) ||
                ModConfig.phantomRepellentItems.contains(serverPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem());
    }

    @ModifyExpressionValue(
            method = "tick",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/util/Mth;clamp(III)I"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/core/BlockPos;above(I)Lnet/minecraft/core/BlockPos;"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 0
            )
    )
    private int modifyCondition(int original, @Local BlockPos playerBlockPos, @Local RandomSource randomSource) {
        if (ModConfig.enablePhantomSpawningRework) {
            return randomSource.nextInt(playerBlockPos.getY());
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "tick",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/util/Mth;clamp(III)I"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/core/BlockPos;above(I)Lnet/minecraft/core/BlockPos;"
                    )
            ),
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=72000"
            )
    )
    private int modifyConditionCheckValue(int original, @Local BlockPos playerBlockPos) {
        if (ModConfig.enablePhantomSpawningRework) {
            return ModConfig.phantomSpawnStartHeight;
        }
        return original;
    }
}
