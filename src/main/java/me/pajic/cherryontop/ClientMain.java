package me.pajic.cherryontop;

import me.pajic.cherryontop.gui.InfoOverlays;
import me.pajic.cherryontop.keybind.CoTKeybinds;
import net.fabricmc.api.ClientModInitializer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CoTKeybinds.initKeybinds();
        InfoOverlays.initOverlay();
    }
}
