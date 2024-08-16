package me.pajic.cherryontop.mixin.early_loaders.faster_obsidian_mining;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.cherryontop.config.EarlyLoader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CryingObsidianBlock;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Definition(id = "OBSIDIAN", field = "Lnet/minecraft/world/level/block/Blocks;OBSIDIAN:Lnet/minecraft/world/level/block/Block;")
    @Expression("OBSIDIAN = ?(?, new ?(?().?(?).?(?).?().?(@(?), ?)))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static float modifyObsidianBreakSpeed(float original) {
        if (EarlyLoader.CONFIG.enableFasterObsidianMining) {
            return 30.0F;
        }
        return original;
    }

    @Definition(id = "CryingObsidianBlock", type = CryingObsidianBlock.class)
    @Expression("new CryingObsidianBlock(?().?(?).?(?).?().?(@(?), ?).?(?))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static float modifyCryingObsidianBreakSpeed(float original) {
        if (EarlyLoader.CONFIG.enableFasterObsidianMining) {
            return 30.0F;
        }
        return original;
    }

    @Definition(id = "RespawnAnchorBlock", type = RespawnAnchorBlock.class)
    @Expression("new RespawnAnchorBlock(?().?(?).?(?).?().?(@(?), ?).?(?))")
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private static float modifyRespawnAnchorBreakSpeed(float original) {
        if (EarlyLoader.CONFIG.enableFasterObsidianMining) {
            return 30.0F;
        }
        return original;
    }
}
