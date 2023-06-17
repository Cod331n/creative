package ru.codein.creative.player;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.jdbi.v3.core.Jdbi;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.rank.Rank;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Реализация интерфейса CreativePlayerDbAPI для получения и сохранения данных об игроке в базе данных.
 */
@RequiredArgsConstructor
public class CreativePlayerDbImpl implements CreativePlayerDbAPI {
    private final Executor executor = Executors.newFixedThreadPool(2);
    private final Jdbi jdbi;
    private final CreativePlayerDao playerDao;

    @Override
    public CompletableFuture<CreativePlayerData> load(String uuid) {
        return CompletableFuture.supplyAsync(() -> {
            CreativePlayerData data = new CreativePlayerData(uuid, Bukkit.getPlayer(UUID.fromString(uuid)).getName(), Rank.NONE);
            CreativePlayerData newData = playerDao.select(uuid);
            if (newData != null) {
                return newData;
            }

            playerDao.insert(data);
            return data;
        }, executor);
    }

    @Override
    public CompletableFuture<Boolean> save(CreativePlayerData data) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                jdbi.useTransaction(handle -> playerDao.update(data));
                return true;
            } catch (Exception exception) {
                return false;
            }
        });
    }


}
