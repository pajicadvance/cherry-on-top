package me.pajic.cherryontop.compat;

import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class CoTSeasonsCompat {

    public static ObjectIntImmutablePair<String> getSeasonStringData(Level level) {
        Season.SubSeason subSeason = SeasonHelper.getSeasonState(level).getSubSeason();

        String seasonName = switch (subSeason) {
            case EARLY_SPRING -> I18n.get("gui.cherry-on-top.early_spring");
            case MID_SPRING -> I18n.get("gui.cherry-on-top.mid_spring");
            case LATE_SPRING -> I18n.get("gui.cherry-on-top.late_spring");
            case EARLY_SUMMER -> I18n.get("gui.cherry-on-top.early_summer");
            case MID_SUMMER -> I18n.get("gui.cherry-on-top.mid_summer");
            case LATE_SUMMER -> I18n.get("gui.cherry-on-top.late_summer");
            case EARLY_AUTUMN -> I18n.get("gui.cherry-on-top.early_autumn");
            case MID_AUTUMN -> I18n.get("gui.cherry-on-top.mid_autumn");
            case LATE_AUTUMN -> I18n.get("gui.cherry-on-top.late_autumn");
            case EARLY_WINTER -> I18n.get("gui.cherry-on-top.early_winter");
            case MID_WINTER -> I18n.get("gui.cherry-on-top.mid_winter");
            case LATE_WINTER -> I18n.get("gui.cherry-on-top.late_winter");
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
