package ru.codein.creative.tab;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.api.v1.TabAPI;
import ru.codein.creative.player.CreativePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class TabImpl implements TabAPI {

    private final CreativePlayerDbAPI creativePlayerDbAPI;
    private final List<BaseComponent[]> header = new ArrayList<>();
    private final List<BaseComponent[]> footer = new ArrayList<>();

    @SneakyThrows
    @Override
    public void update(Player player) {
        updatePlayerListName(player);
    }

    @Override
    public void addHeaderLine(TextComponent text) {
        header.add(new BaseComponent[]{withColor(text.toPlainText())});
    }

    @Override
    public void addFooterLine(TextComponent text) {
        footer.add(new BaseComponent[]{withColor(text.toPlainText())});
    }

    private void updatePlayerListName(Player player) {
        Bukkit.getScheduler().runTask(Creative.getPlugin(), () -> {
            CreativePlayer creativePlayer;

            try {
                creativePlayer = new CreativePlayer().load(creativePlayerDbAPI.load(player.getUniqueId().toString()).get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            if (creativePlayer == null) {
                return;
            }

            String formattedName = creativePlayer.getFormattedName();
            player.setPlayerListName(formattedName);
            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) player).getHandle());
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }

    private BaseComponent withColor(String text) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', text));
    }
}
