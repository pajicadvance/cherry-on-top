package me.pajic.cherryontop.compat;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import me.pajic.cherryontop.item.EnderBackpackItem;
import me.pajic.cherryontop.item.TrinketEnderBackpackItem;
import me.pajic.cherryontop.network.CoTNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Optional;

public class CoTTrinketsCompat {

    public static void tryOpenEnderBackpack(Player player) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(player);
        if (trinketComponent.isPresent() && trinketComponent.get().isEquipped(
                itemStack -> itemStack.getItem() instanceof EnderBackpackItem
        )) {
            ClientPlayNetworking.send(new CoTNetworking.C2SOpenEnderContainerPayload());
        }
    }

    public static void register() {
        EnderBackpackItem item = new TrinketEnderBackpackItem(new Item.Properties().stacksTo(1));
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.parse("cherry-on-top:ender_backpack"),
                item
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(contents -> contents.addAfter(
                Items.LEAD,
                item
        ));
    }
}
