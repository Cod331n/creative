package ru.codein.creative.player;

import lombok.Data;
import org.bukkit.ChatColor;
import ru.codein.creative.rank.Rank;

/**
 * Класс CreativePlayer содержит информацию об игроке: его UUID, имени и ранге.
 */
@Data
public class CreativePlayer {
    private String uuid;
    private String name;
    private Rank rank;

    /**
     * Метод загружает данные игрока из объекта CreativePlayerData и устанавливает их в соответствующие поля класса CreativePlayer.
     *
     * @param playerData Объект CreativePlayerData, содержащий данные игрока.
     */
    public CreativePlayer load(CreativePlayerData playerData) {
        uuid = playerData.getUuid();
        name = playerData.getName();
        rank = playerData.getRank();
        return this;
    }

    /**
     * Метод преобразует объект CreativePlayer в объект CreativePlayerData.
     *
     * @return Объект CreativePlayerData, содержащий данные игрока.
     */
    public CreativePlayerData convertToData() {
        return new CreativePlayerData(uuid, name, rank);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public String getFormattedName() {
        String format = new StringBuilder()
                .append(rank.getPrefixColor())
                .append(rank.getPrefix())
                .append(" &f")
                .append(name).toString();
        return ChatColor.translateAlternateColorCodes('&', format);
    }
}







