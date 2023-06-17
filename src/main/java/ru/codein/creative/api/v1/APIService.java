package ru.codein.creative.api.v1;

import lombok.Getter;
import ru.codein.creative.Creative;
import ru.codein.creative.db.DatabaseConnector;
import ru.codein.creative.rank.RankImpl;
import ru.codein.creative.player.CreativePlayerDbImpl;

/**
 * Класс APIService хранит в себе все экземляры классов для работы с API
 **/
@Getter
public class APIService {
    private final RankAPI rankAPI = new RankImpl();
    private final CreativePlayerDbAPI creativePlayerDbAPI = new CreativePlayerDbImpl(new DatabaseConnector().getJdbi(), Creative.getPlugin().getCreativePlayerDao());

}
