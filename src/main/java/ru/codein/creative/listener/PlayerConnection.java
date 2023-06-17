package ru.codein.creative.listener;

import lombok.SneakyThrows;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.APIService;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.player.CreativePlayerData;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;

public class PlayerConnection implements Listener {

    private final CreativePlayerDao creativePlayerDao = Creative.getPlugin().getCreativePlayerDao();
    private final CreativePlayerDbAPI creativePlayerDbAPI = new APIService().getCreativePlayerDbAPI();
    @SneakyThrows
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CreativePlayerData creativePlayerData =  creativePlayerDbAPI.load(event.getPlayer().getUniqueId().toString()).get();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }
}
