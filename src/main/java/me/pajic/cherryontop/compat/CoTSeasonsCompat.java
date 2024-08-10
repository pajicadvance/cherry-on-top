package me.pajic.cherryontop.compat;

import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class CoTSeasonsCompat {

    public static ObjectIntImmutablePair<Component> getSeasonStringData(Level level) {
        Season.SubSeason subSeason = SeasonHelper.getSeasonState(level).getSubSeason();

        Component seasonName = switch (subSeason) {
            case EARLY_SPRING -> Component.translatable("gui.cherry-on-top.early_spring");
            case MID_SPRING -> Component.translatable("gui.cherry-on-top.mid_spring");
            case LATE_SPRING -> Component.translatable("gui.cherry-on-top.late_spring");
            case EARLY_SUMMER -> Component.translatable("gui.cherry-on-top.early_summer");
            case MID_SUMMER -> Component.translatable("gui.cherry-on-top.mid_summer");
            case LATE_SUMMER -> Component.translatable("gui.cherry-on-top.late_summer");
            case EARLY_AUTUMN -> Component.translatable("gui.cherry-on-top.early_autumn");
            case MID_AUTUMN -> Component.translatable("gui.cherry-on-top.mid_autumn");
            case LATE_AUTUMN -> Component.translatable("gui.cherry-on-top.late_autumn");
            case EARLY_WINTER -> Component.translatable("gui.cherry-on-top.early_winter");
            case MID_WINTER -> Component.translatable("gui.cherry-on-top.mid_winter");
            case LATE_WINTER -> Component.translatable("gui.cherry-on-top.late_winter");
        };

        int seasonColor = switch (subSeason.getSeason()) {
            case Season.SPRING -> 0x42F55A;
            case Season.SUMMER -> 0xF2F542;
            case Season.AUTUMN -> 0xF57542;
            case Season.WINTER -> 0x42F5F5;
        };

        return ObjectIntImmutablePair.of(seasonName, seasonColor);
    }
}
