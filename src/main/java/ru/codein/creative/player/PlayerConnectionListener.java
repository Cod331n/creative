package ru.codein.creative.player;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.chat.MessageSender;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.permission.PermissionService;

public class PlayerConnectionListener implements Listener {

    private final CreativePlayerDao creativePlayerDao = Creative.getPlugin().getCreativePlayerDao();
    private final CreativePlayerDbAPI creativePlayerDbAPI = Creative.getPlugin().getApiService().getCreativePlayerDbAPI();

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, Creative.getPlugin());
    }

    public void unregister() {
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }
    @SneakyThrows
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CreativePlayerData creativePlayerData =  creativePlayerDbAPI.load(event.getPlayer().getUniqueId().toString()).get();
        CreativePlayer creativePlayer = new CreativePlayer().load(creativePlayerData);

        Creative.getPlugin().getApiService().getTabAPI().update(event.getPlayer());
        MessageSender.sendMessage(creativePlayer, "Привет!");

        PermissionService permissionAPI = new PermissionService();
        permissionAPI.reloadPermissions(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }
}
