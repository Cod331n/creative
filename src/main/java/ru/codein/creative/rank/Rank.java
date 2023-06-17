package ru.codein.creative.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;

@Getter
@AllArgsConstructor
public enum Rank {
    NONE(10, "NONE", "N", "&8", "&8"),
    MEMBER(20, "MEMBER", "M", "&6", "&8"),
    NOVICE(30, "NOVICE", "N", "&b", "&8"),
    MASTER(40, "MASTER", "M", "&5", "&8"),
    ELITE(50, "ELITE", "E", "&c", "&8"),
    RT(60, "RT", "RT", "&e", "&8"),
    LS(70, "LS", "LS", "&c", "&8");

    private final int priority;
    private final String name;
    private final String prefix;
    private final String prefixColor;
    private final String chatColor;

    public static Comparator<Rank> byPriority() {
        return Comparator.comparingInt(Rank::getPriority);
    }

    public static Rank getDefault() {
        return Rank.NONE;
    }
}
