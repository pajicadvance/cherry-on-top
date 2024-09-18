package me.pajic.cherryontop.mixin.early_loaders.compat.snha;

import com.jerrylu086.netherite_horse_armor.NetheriteHorseArmor;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.cherryontop.config.EarlyLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@IfModLoaded("netherite_horse_armor")
@Mixin(NetheriteHorseArmor.class)
public class NetheriteHorseArmorMixin {

    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"
            )
    )
    private static int modifyNetheriteHorseArmorStackSize(int original) {
        if (EarlyLoader.CONFIG.enableStackableHorseArmor) {
            return EarlyLoader.CONFIG.horseArmorMaxStackSize;
        }
        return original;
    }
}
