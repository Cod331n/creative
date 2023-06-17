package ru.codein.creative.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.codein.creative.player.CreativePlayerData;
import ru.codein.creative.dao.mapper.CreativePlayerMapper;

/**
 * <b><font color="gray">Data Access Object </font><i>(DAO)</i></b> для работы с таблицей creative_players.
 * Предоставляет методы для создания таблицы, выборки данных,
 * вставки и обновления записей.
 */
@RegisterRowMapper(CreativePlayerMapper.class)
public interface CreativePlayerDao {

    /**
     * Создает таблицу <b>creative_players</b>, если она не существует.
     */
    @SqlUpdate("CREATE TABLE IF NOT EXISTS creative_players (uuid VARCHAR(255) PRIMARY KEY, name TEXT, rank TEXT)")
    void createTable();

    /**
     * Выполняет выборку данных игрока по его UUID (уникальному идентификатору).
     *
     * @param uuid UUID игрока.
     * @return Объект CreativePlayerData, содержащий данные игрока,
     * или null, если запись не найдена.
     */
    @SqlQuery("SELECT uuid, name, rank FROM creative_players WHERE uuid = :uuid")
    CreativePlayerData select(@Bind("uuid") String uuid);

    /**
     * Вставляет новую запись с данными игрока в таблицу.
     *
     * @param data Объект CreativePlayerData, содержащий данные игрока.
     */
    @SqlUpdate("INSERT INTO creative_players (uuid, name, rank) VALUES (:uuid, :name, :rank)")
    void insert(@BindBean CreativePlayerData data);

    /**
     * Обновляет существующую запись с данными игрока в таблице.
     *
     * @param data Объект CreativePlayerData, содержащий обновленные данные игрока.
     */
    @SqlUpdate("UPDATE creative_players SET uuid = :uuid, name = :name, rank = :rank WHERE uuid = :uuid")
    void update(@BindBean CreativePlayerData data);
}
