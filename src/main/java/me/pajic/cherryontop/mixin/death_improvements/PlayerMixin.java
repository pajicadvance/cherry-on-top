package me.pajic.cherryontop.mixin.death_improvements;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.cherryontop.Main;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @WrapMethod(method = "getBaseExperienceReward")
    private int modifyDroppedXpOnDeath(Operation<Integer> original) {
        Player self = (Player) (Object) this;
        if (Main.CONFIG.deathImprovements.playerDropMoreXpOnDeath() && !self.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            int xp = 0;
            int level = self.experienceLevel;
            for (int i = 0; i < level; i++) {
                self.experienceLevel = i;
                xp += self.getXpNeededForNextLevel();
            }
            self.experienceLevel = level;
            xp += (int) (self.experienceProgress * self.getXpNeededForNextLevel());
            return (int) (xp * (float) Main.CONFIG.deathImprovements.droppedExperiencePercent() / 100);
        }
        return original.call();
    }

    @ModifyArgs(
            method = "drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/ItemEntity;setDeltaMovement(DDD)V",
                    ordinal = 1
            )
    )
    private void preventItemThrowOnDeath(Args args) {
        Player self = (Player) (Object) this;
        if (Main.CONFIG.deathImprovements.noItemSplatterOnDeath() && self.isDeadOrDying()) {
            args.setAll(0.0d, 0.0d, 0.0d);
        }
    }
}
