package ru.codein.creative.dao.mapper;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import ru.codein.creative.player.CreativePlayerData;
import ru.codein.creative.rank.Rank;

import java.sql.Clob;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Класс-маппер для преобразования данных из результата запроса базы данных
 * в объект типа CreativePlayerData.
 */
public class CreativePlayerMapper implements RowMapper<CreativePlayerData> {

    /**
     * Метод выполняет преобразование данных из результата запроса базы данных
     * в объект CreativePlayerData.
     *
     * @param rs  ResultSet, содержащий данные из результата запроса.
     * @param ctx StatementContext, контекст выполнения операции.
     * @return Объект CreativePlayerData, содержащий данные игрока.
     */
    @Override
    public CreativePlayerData map(ResultSet rs, StatementContext ctx) {
        try {
            String uuid = rs.getString("UUID");
            String name = rs.getString("NAME");

            Clob clob = rs.getClob("RANK");
            String rankStr = clob.getSubString(0, (int) clob.length());
            Rank rank = Rank.valueOf(rankStr);

            return new CreativePlayerData(uuid, name, rank);
        } catch (SQLException e) {
            System.out.println("Exception while creating CreativePlayerData in: " + CreativePlayerMapper.class.getName());
        }
        return null;
    }
}
