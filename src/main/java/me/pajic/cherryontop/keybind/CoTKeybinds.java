package me.pajic.cherryontop.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.compat.CoTTrinketsCompat;
import me.pajic.cherryontop.item.EnderBackpackItem;
import me.pajic.cherryontop.network.CoTNetworking;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class CoTKeybinds {

    private static final KeyMapping OPEN_ENDER_BACKPACK = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.cherry-on-top.open_ender_backpack",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_B,
                    "category.cherry-on-top.keybindings"
            )
    );

    private static final KeyMapping NEXT_MUSIC_TRACK = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.cherry-on-top.next_music_track",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_M,
                    "category.cherry-on-top.keybindings"
            )
    );

    public static void initKeybinds() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_ENDER_BACKPACK.consumeClick() && client.player != null) {
                if (client.player.getInventory().hasAnyMatching(itemStack -> itemStack.getItem() instanceof EnderBackpackItem)) {
                    ClientPlayNetworking.send(new CoTNetworking.C2SOpenEnderContainerPayload());
                }
                else if (FabricLoader.getInstance().isModLoaded("trinkets")) {
                    CoTTrinketsCompat.tryOpenEnderBackpack(client.player);
                }
            }
            if (Main.CONFIG.musicControl.enableNextTrackKeybind() && NEXT_MUSIC_TRACK.consumeClick() && client.player != null) {
                client.getMusicManager().stopPlaying();
                client.getMusicManager().startPlaying(client.getSituationalMusic());
            }
        });
    }
}
