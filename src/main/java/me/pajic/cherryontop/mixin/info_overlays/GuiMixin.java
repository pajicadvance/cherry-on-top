package me.pajic.cherryontop.mixin.info_overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.cherryontop.Main;
import me.pajic.cherryontop.compat.CoTEmiCompat;
import me.pajic.cherryontop.compat.CoTSeasonsCompat;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ConstantConditions")
@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void renderInfoOverlays(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
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
                renderCompassOverlay(guiGraphics);
                renderClockOverlay(guiGraphics, true);
            }
            else if (Main.CONFIG.infoOverlays.enableCompassOverlay() && hasCompass(minecraft.player)) {
                renderCompassOverlay(guiGraphics);
            }
            else if (Main.CONFIG.infoOverlays.enableClockOverlay() && hasClock(minecraft.player)) {
                renderClockOverlay(guiGraphics, false);
            }
        }
    }

    @Unique
    private void renderCompassOverlay(GuiGraphics guiGraphics) {
        Font font = minecraft.gui.getFont();
        BlockPos blockPos = minecraft.player.blockPosition();
        ResourceLocation biome = minecraft.player.level().getBiome(blockPos).unwrap().map(
                key -> key != null ? key.location() : null, unknown -> null
        );

        String coordinates = I18n.get(
                "gui.cherry-on-top.coordinates",
                blockPos.getX(), blockPos.getY(), blockPos.getZ()
        );
        String direction = I18n.get("gui.cherry-on-top.facing", minecraft.player.getDirection().getName());
        String biomeName = I18n.get("biome." + biome.getNamespace() + "." + biome.getPath());

        int y = 4;
        renderLine(guiGraphics, font, coordinates, y, 0xffffff);
        y += 12;
        renderLine(guiGraphics, font, direction, y, 0xffffff);
        y += 12;
        renderLine(guiGraphics, font, biomeName, y, 0xffffff);
    }

    @Unique
    private void renderClockOverlay(GuiGraphics guiGraphics, boolean compassRendered) {
        Font font = minecraft.gui.getFont();
        BlockPos blockPos = minecraft.player.blockPosition();
        int y;

        String day = I18n.get("gui.cherry-on-top.day", minecraft.level.getDayTime() / 24000L);
        long timeOffset = (minecraft.level.getDayTime() + 6000) % 24000;
        String time = I18n.get(
                "gui.cherry-on-top.time",
                timeOffset / 1000,
                String.format("%02d", (int) ((double) (timeOffset / 10 % 100) / 100 * 60))
        );
        if (compassRendered) y = 40;
        else y = 4;
        renderLine(guiGraphics, font, day + ", " + time, y, 0xffffff);

        if (FabricLoader.getInstance().isModLoaded("sereneseasons")) {
            ObjectIntImmutablePair<String> seasonStringData = CoTSeasonsCompat.getSeasonStringData(minecraft.level);
            y += 12;
            if (Main.CONFIG.infoOverlays.coloredSeason())
                renderLine(guiGraphics, font, seasonStringData.left(), y, seasonStringData.rightInt());
            else
                renderLine(guiGraphics, font, seasonStringData.left(), y, 0xffffff);
        }

        String weather;
        int weatherColor;
        if (minecraft.level.isThundering()) {
            weather = I18n.get("gui.cherry-on-top.thundering");
            weatherColor = 0x2d3656;
        }
        else if (minecraft.level.isRaining()) {
            Biome.Precipitation precipitation = minecraft.level.getBiome(blockPos).value().getPrecipitationAt(blockPos);
            if (precipitation == Biome.Precipitation.RAIN) {
                weather = I18n.get("gui.cherry-on-top.raining");
                weatherColor = 0x30639c;
            }
            else if (precipitation == Biome.Precipitation.SNOW) {
                weather = I18n.get("gui.cherry-on-top.snowing");
                weatherColor = 0x2fced2;
            }
            else {
                weather = I18n.get("gui.cherry-on-top.cloudy");
                weatherColor = 0x878787;
            }
        }
        else {
            weather = I18n.get("gui.cherry-on-top.clear");
            weatherColor = 0xffffff;
        }
        y += 12;
        if (Main.CONFIG.infoOverlays.coloredWeather())
            renderLine(guiGraphics, font, weather, y, weatherColor);
        else
            renderLine(guiGraphics, font, weather, y, 0xffffff);
    }

    @Unique
    private boolean hasCompass(LocalPlayer player) {
        return player.getInventory().hasAnyMatching(itemStack -> itemStack.is(Items.COMPASS));
    }

    @Unique
    private boolean hasClock(LocalPlayer player) {
        return player.getInventory().hasAnyMatching(itemStack -> itemStack.is(Items.CLOCK));
    }

    @Unique
    private void renderLine(GuiGraphics guiGraphics, Font font, String text, float y, int color) {
        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(4, y, 0.0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.drawString(font, text, 0, 0, color);
        RenderSystem.disableBlend();
        matrixStack.popPose();
    }
}
