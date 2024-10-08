package me.pajic.cherryontop.mixin.compat.notes;

import com.chaosthedude.notes.gui.EditNoteScreen;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.cherryontop.Main;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("notes")
@Mixin(EditNoteScreen.class)
public abstract class EditNoteScreenMixin extends Screen {

    protected EditNoteScreenMixin(Component title) {
        super(title);
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/chaosthedude/notes/gui/NotesTextField;isFocused()Z"
            )
    )
    private boolean disableButtonsIfNoCompassFound(boolean original) {
        if (
                Main.CONFIG.hideDebugInfoInSurvival() &&
                minecraft != null && minecraft.player != null && minecraft.level != null
        ) {
            return original && minecraft.player.getInventory().hasAnyMatching(itemStack -> itemStack.is(Items.COMPASS));
        }
        return original;
    }
}