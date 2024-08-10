package me.pajic.cherryontop.mixin.player_drops_more_xp;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.cherryontop.Main;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @WrapMethod(method = "getBaseExperienceReward")
    private int modifyDroppedXpOnDeath(Operation<Integer> original) {
        Player self = (Player) (Object) this;
        if (Main.CONFIG.playerDropMoreXpOnDeath() && !self.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            int xp = 0;
            int level = self.experienceLevel;
            for (int i = 0; i < level; i++) {
                self.experienceLevel = i;
                xp += self.getXpNeededForNextLevel();
            }
            self.experienceLevel = level;
            xp += (int) (self.experienceProgress * self.getXpNeededForNextLevel());
            return (int) (xp * (float) Main.CONFIG.droppedExperiencePercent() / 100);
        }
        return original.call();
    }
}
