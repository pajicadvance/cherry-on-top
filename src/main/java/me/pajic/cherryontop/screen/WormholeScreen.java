package me.pajic.cherryontop.screen;

import me.pajic.cherryontop.network.CoTNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class WormholeScreen extends Screen {

    private final Player user;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private PlayerListWidget list;

    public WormholeScreen(Player user) {
        super(Component.translatable("screen.cherry-on-top.wormholePlayerSelection.title"));
        this.user = user;
    }

    @Override
    protected void init() {
        LinearLayout linearLayout = this.layout.addToHeader(LinearLayout.vertical().spacing(8));
        linearLayout.defaultCellSetting().alignHorizontallyCenter();
        linearLayout.addChild(new StringWidget(this.getTitle(), this.font));
        this.list = this.layout.addToContents(new PlayerListWidget());
        LinearLayout linearLayout2 = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        linearLayout2.addChild(Button.builder(CommonComponents.GUI_DONE, button -> {
            ClientPlayNetworking.send(new CoTNetworking.C2SWormholeTeleportPayload(list.getSelected().player.getUUID()));
            this.onClose();
        }).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        this.list.updateSize(this.width, this.layout);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    private class PlayerListWidget extends ObjectSelectionList<PlayerEntry> {

        public PlayerListWidget() {
            super(
                    WormholeScreen.this.minecraft,
                    WormholeScreen.this.width,
                    WormholeScreen.this.height - 77,
                    40,
                    16
            );

            ClientLevel level = this.minecraft.level;
            level.players().stream().filter(player -> !player.is(user)).forEach(player ->
                    addEntry(new PlayerEntry(level.getPlayerByUUID(player.getUUID()))));
            setSelected(children().stream().findFirst().get());
            centerScrollOn(children().stream().findFirst().get());
        }
    }

    private class PlayerEntry extends ObjectSelectionList.Entry<PlayerEntry> {

        private final Player player;

        public PlayerEntry(Player player) {
            this.player = player;
        }

        @Override
        public @NotNull Component getNarration() {
            return Component.translatable("narrator.select", player.getName());
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            guiGraphics.drawString(
                    WormholeScreen.this.font,
                    player.getName(),
                    left + 5,
                    top + 2,
                    16777215
            );
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            list.setSelected(this);
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }
}
