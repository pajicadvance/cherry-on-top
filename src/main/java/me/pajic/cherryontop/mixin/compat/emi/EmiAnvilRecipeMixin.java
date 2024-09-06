package me.pajic.cherryontop.mixin.compat.emi;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import dev.emi.emi.recipe.EmiAnvilRecipe;
import me.pajic.cherryontop.util.CoTUtil;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("emi")
@Mixin(value = EmiAnvilRecipe.class, remap = false)
public class EmiAnvilRecipeMixin {

    @ModifyExpressionValue(
            method = "getTool",
            at = @At(
                    value = "CONSTANT",
                    args = "intValue=4"
            )
    )
    private int modifyUnitCost(int original, @Local ItemStack stack) {
        return CoTUtil.determineUnitCost(stack);
    }
}
