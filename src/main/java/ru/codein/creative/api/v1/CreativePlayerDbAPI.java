package ru.codein.creative.api.v1;

import ru.codein.creative.player.CreativePlayerData;

import java.util.concurrent.CompletableFuture;

/**
 * Интерфейс для получения данных об игроке из базы данных и сохранения данных в базу данных.
 */
public interface CreativePlayerDbAPI {

    /**
     * Выгружает данные игрока из базы данных. Если данные отсутствуют, создает и возвращает новый объект CreativePlayerData.
     *
     * @param uuid UUID (личный идентификатор) игрока
     * @return CompletableFuture, который завершается с объектом CreativePlayerData
     */
    CompletableFuture<CreativePlayerData> load(String uuid);

    /**
     * Сохраняет данные игрока в базу данных.
     *
     * @param data данные игрока
     * @return CompletableFuture, который завершается значением true, если сохранение прошло успешно, иначе false
     */
    @SuppressWarnings("UnusedReturnValue")
    CompletableFuture<Boolean> save(CreativePlayerData data);
}
