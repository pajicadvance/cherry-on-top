package me.pajic.cherryontop.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.wispforest.owo.ui.core.Color;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.compat.CoTEmiCompat;
import me.pajic.cherryontop.compat.CoTSeasonsCompat;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public class InfoOverlays {

    public static void initOverlay() {
        HudRenderCallback.EVENT.register(InfoOverlay.INSTANCE::render);
    }

    @SuppressWarnings("ConstantConditions")
    public static class InfoOverlay implements LayeredDraw.Layer {

        protected static final InfoOverlay INSTANCE = new InfoOverlay();

        @Override
        public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
            Minecraft minecraft = Minecraft.getInstance();
            if (
                    minecraft.player != null && minecraft.level != null &&
                            !minecraft.options.hideGui && !minecraft.gui.getDebugOverlay().showDebugScreen()

            ) {
                if (FabricLoader.getInstance().isModLoaded("emi") && CoTEmiCompat.Methods.isEmiScreenOpen()) {
                    return;
                }
                if (
                        Main.CONFIG.infoOverlays.enableCompassOverlay() && Main.CONFIG.infoOverlays.enableClockOverlay() &&
                                hasCompass(minecraft.player) && hasClock(minecraft.player)
                ) {
                    renderCompassOverlay(guiGraphics, minecraft);
                    renderClockOverlay(guiGraphics, true, minecraft);
                }
                else if (Main.CONFIG.infoOverlays.enableCompassOverlay() && hasCompass(minecraft.player)) {
                    renderCompassOverlay(guiGraphics, minecraft);
                }
                else if (Main.CONFIG.infoOverlays.enableClockOverlay() && hasClock(minecraft.player)) {
                    renderClockOverlay(guiGraphics, false, minecraft);
                }
            }
        }

        private void renderCompassOverlay(GuiGraphics guiGraphics, Minecraft minecraft) {
            Font font = minecraft.gui.getFont();
            BlockPos blockPos = minecraft.player.blockPosition();
            ResourceLocation biome = minecraft.player.level().getBiome(blockPos).unwrap().map(
                    key -> key != null ? key.location() : null, unknown -> null
            );

            Component coordinates = Component.translatable(
                    "gui.cherry-on-top.coordinates",
                    blockPos.getX(), blockPos.getY(), blockPos.getZ()
            );
            Component direction = Component.translatable("gui.cherry-on-top.facing", minecraft.player.getDirection().getName());
            Component biomeName = Component.translatable("biome." + biome.getNamespace() + "." + biome.getPath());

            int y = 4;
            renderLine(guiGraphics, font, coordinates, y, 0xffffff);
            y += 12;
            renderLine(guiGraphics, font, direction, y, 0xffffff);
            y += 12;
            renderLine(guiGraphics, font, biomeName, y, 0xffffff);
        }

        private void renderClockOverlay(GuiGraphics guiGraphics, boolean compassRendered, Minecraft minecraft) {
            Font font = minecraft.gui.getFont();
            BlockPos blockPos = minecraft.player.blockPosition();
            int y;

            MutableComponent dayAndTime = Component.translatable(
                    "gui.cherry-on-top.day",
                    (minecraft.level.getDayTime() / 24000L) + 1
            );
            long timeOffset = (minecraft.level.getDayTime() + 6000) % 24000;
            Component time = Component.translatable(
                    "gui.cherry-on-top.time",
                    timeOffset / 1000,
                    String.format("%02d", (int) ((double) (timeOffset / 10 % 100) / 100 * 60))
            );
            dayAndTime.append(", ");
            dayAndTime.append(time);
            if (compassRendered) y = 40;
            else y = 4;
            renderLine(guiGraphics, font, dayAndTime, y, 0xffffff);

            if (FabricLoader.getInstance().isModLoaded("sereneseasons")) {
                ObjectIntImmutablePair<Component> seasonStringData = CoTSeasonsCompat.getSeasonStringData(minecraft.level);
                y += 12;
                if (Main.CONFIG.infoOverlays.coloredSeason())
                    renderLine(guiGraphics, font, seasonStringData.left(), y, seasonStringData.rightInt());
                else
                    renderLine(guiGraphics, font, seasonStringData.left(), y, 0xffffff);
            }

            Component weather;
            int weatherColor;
            if (minecraft.level.isThundering()) {
                weather = Component.translatable("gui.cherry-on-top.thundering");
                weatherColor = 0x2d3656;
            }
            else if (minecraft.level.isRaining()) {
                Biome.Precipitation precipitation = minecraft.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
                if (precipitation == Biome.Precipitation.RAIN) {
                    weather = Component.translatable("gui.cherry-on-top.raining");
                    weatherColor = 0x30639c;
                }
                else if (precipitation == Biome.Precipitation.SNOW) {
                    weather = Component.translatable("gui.cherry-on-top.snowing");
                    weatherColor = 0x2fced2;
                }
                else {
                    weather = Component.translatable("gui.cherry-on-top.cloudy");
                    weatherColor = 0x878787;
                }
            }
            else {
                weather = Component.translatable("gui.cherry-on-top.clear");
                weatherColor = 0xffffff;
            }
            y += 12;
            if (Main.CONFIG.infoOverlays.coloredWeather())
                renderLine(guiGraphics, font, weather, y, weatherColor);
            else
                renderLine(guiGraphics, font, weather, y, 0xffffff);
        }

        private boolean hasCompass(LocalPlayer player) {
            return player.getInventory().hasAnyMatching(itemStack -> itemStack.is(Items.COMPASS));
        }

        private boolean hasClock(LocalPlayer player) {
            return player.getInventory().hasAnyMatching(itemStack -> itemStack.is(Items.CLOCK));
        }

        private void renderLine(GuiGraphics guiGraphics, Font font, Component text, int y, int color) {
            RenderSystem.enableBlend();

            if (Main.CONFIG.infoOverlays.textBackground()) {
                guiGraphics.fill(
                        2, y - 2, font.width(text) + 5, y + 10,
                        Color.ofHsv(0, 0, 0, Main.CONFIG.infoOverlays.textBackgroundOpacity()).argb()
                );
            }
            guiGraphics.drawString(font, text, 4, y, color, Main.CONFIG.infoOverlays.textShadow());

            RenderSystem.disableBlend();
        }
    }
}
