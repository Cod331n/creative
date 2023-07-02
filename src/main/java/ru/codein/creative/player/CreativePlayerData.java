package ru.codein.creative.player;

import lombok.Value;
import ru.codein.creative.rank.Rank;

/**
 * Класс CreativePlayerData представляет данные игрока: содержит информацию о его UUID, имени и ранге.
 */
@Value
public class CreativePlayerData {
    String uuid;
    String name;
    Rank rank;
}
