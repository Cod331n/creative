package ru.codein.creative.api.v1;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import ru.codein.creative.rank.Rank;

/**
 * Интерфейс для управления рангами игроков и их правами.
 */
public interface RankAPI {

    /**
     * Устанавливает указанный ранг для игрока.
     *
     * @param player игрок, которому будет установлен ранг
     * @param rank   устанавливаемый ранг
     * @return
     */
    boolean setRank(Player player, Rank rank);

    /**
     * Устанавливает игроку ранг по умолчанию
     *
     * @param player игрок, у которого будет удален ранг
     * @return
     */
    boolean removeRank(Player player);

    /**
     * Получает текущий ранг игрока.
     *
     * @param player игрок, для которого будет получен ранг
     * @return текущий ранг игрока, или null, если игрок не имеет ранга
     */
    Rank getRank(Player player);

    /**
     * Возвращает следующий ранг после текущего ранга.
     *
     * @param currentRank текущий ранг
     * @return следующий ранг после текущего
     */
    Rank getNextRank(Rank currentRank);

    /**
     * Возвращает предыдущий ранг от текущего.
     *
     * @param currentRank текущий ранг
     * @return следующий ранг после текущего
     */
    Rank getPreviousRank(Rank currentRank);

    /**
     * Добавляет указанное разрешение (право) к игроку.
     *
     * @param player               игрок, которому будет добавлено разрешение
     * @param permissionAttachment объект PermissionAttachment с добавляемым разрешением
     */
    void addPermission(Player player, PermissionAttachment permissionAttachment);

    /**
     * Удаляет указанное разрешение (право) у игрока.
     *
     * @param player               игрок, у которого будет удалено разрешение
     * @param permissionAttachment объект PermissionAttachment с удаляемым разрешением
     */
    void removePermission(Player player, PermissionAttachment permissionAttachment);

    /**
     * Проверяет, является ли ранг rank1 выше ранга rank2 в иерархии.
     *
     * @param rank1 первый ранг для сравнения
     * @param rank2 второй ранг для сравнения
     * @return <b>true</b>, если rank1 выше rank2, иначе <b>false</b>
     */
    boolean isHigher(Rank rank1, Rank rank2);
}
