package ru.codein.creative.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.codein.creative.Creative;
import ru.codein.creative.chat.MessageSender;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public boolean hasPermission(Player player, String perm) {
        FileConfiguration config = Creative.getPlugin().getConfig();
        String rankName = this.name().toLowerCase();
        ConfigurationSection groupSection = config.getConfigurationSection("groups." + rankName);

        if (groupSection != null) {
            List<String> permissions = groupSection.getStringList("permissions");

            Optional<String> hasPermission = permissions.stream().filter(perm::equals).findAny();

            if (hasPermission.isPresent()) {
                return true;
            } else {
                MessageSender.sendError(player, "У вас нет прав");
                return false;
            }
        }
        return false;
    }
}
