package me.pajic.cherryontop.mixin.phantom_spawning_rework;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.cherryontop.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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
        if (Main.CONFIG.phantomSpawningRework.enablePhantomSpawningRework()) {
            Function rand = new Function("rand", 1) {
                @Override
                public double apply(double... args) {
                    return 1 + randomSource.nextInt((int)args[0]);
                }
            };
            try {
                Expression expression = new ExpressionBuilder(Main.CONFIG.phantomSpawningRework.phantomSpawnFrequency())
                        .function(rand)
                        .build();
                if (expression.validate().isValid()) {
                    int newValue = (int)expression.evaluate();
                    if (newValue > 0) {
                        instance.nextTick += newValue * 20;
                    }
                }
            } catch (Exception ignored) {}
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
        if (Main.CONFIG.phantomSpawningRework.enablePhantomSpawningRework()) {
            if (original || !Main.CONFIG.phantomSpawningRework.repelPhantomsWithDefinedItems()) {
                return true;
            }
            return Main.CONFIG.phantomSpawningRework.phantomRepellentItems().contains(getNamespaceAndPath(serverPlayer.getItemInHand(InteractionHand.MAIN_HAND))) ||
                    Main.CONFIG.phantomSpawningRework.phantomRepellentItems().contains(getNamespaceAndPath(serverPlayer.getItemInHand(InteractionHand.OFF_HAND)));
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I",
                    ordinal = 0
            )
    )
    private int modifyCondition(int original, @Local BlockPos playerBlockPos, @Local RandomSource randomSource) {
        if (Main.CONFIG.phantomSpawningRework.enablePhantomSpawningRework()) {
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
        if (Main.CONFIG.phantomSpawningRework.enablePhantomSpawningRework()) {
            return Main.CONFIG.phantomSpawningRework.phantomSpawnStartHeight();
        }
        return original;
    }

    @Unique
    private String getNamespaceAndPath(ItemStack itemStack) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        return resourceLocation.getNamespace() + ":" + resourceLocation.getPath();
    }
}
