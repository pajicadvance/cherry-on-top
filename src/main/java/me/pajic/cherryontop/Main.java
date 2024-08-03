package me.pajic.cherryontop;

import me.pajic.cherryontop.config.CoTConfig;
import me.pajic.cherryontop.data.CoTData;
import me.pajic.cherryontop.item.CoTItems;
import me.pajic.cherryontop.keybind.CoTKeybinds;
import me.pajic.cherryontop.network.CoTNetworking;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

    public static final CoTConfig CONFIG = CoTConfig.createAndLoad();

    @Override
    public void onInitialize() {
        CoTItems.initItems();
        CoTData.initData();
        CoTNetworking.initNetworking();
        CoTKeybinds.initKeybinds();
    }
}
