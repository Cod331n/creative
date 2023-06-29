package ru.codein.creative.listener;

import lombok.SneakyThrows;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.chat.MessageSender;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.player.CreativePlayer;
import ru.codein.creative.player.CreativePlayerData;

public class PlayerConnection implements Listener {

    private final CreativePlayerDao creativePlayerDao = Creative.getPlugin().getCreativePlayerDao();
    private final CreativePlayerDbAPI creativePlayerDbAPI = Creative.getPlugin().getApiService().getCreativePlayerDbAPI();
    @SneakyThrows
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        CreativePlayerData creativePlayerData =  creativePlayerDbAPI.load(event.getPlayer().getUniqueId().toString()).get();
        CreativePlayer creativePlayer = new CreativePlayer().load(creativePlayerData);

        Creative.getPlugin().getApiService().getTabAPI().update(event.getPlayer());
        MessageSender.sendMessage(creativePlayer, "Привет!");

        CraftPlayer craftPlayer = (CraftPlayer) event.getPlayer();
        EntityPlayer entityPlayer = craftPlayer.getHandle();

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }
}
