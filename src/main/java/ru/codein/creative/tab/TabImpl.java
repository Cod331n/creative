package ru.codein.creative.tab;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.api.v1.TabAPI;
import ru.codein.creative.player.CreativePlayer;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TabImpl implements TabAPI {

    private final CreativePlayerDbAPI creativePlayerDbAPI;
    private final List<BaseComponent[]> header = new ArrayList<>();
    private final List<BaseComponent[]> footer = new ArrayList<>();

    @SneakyThrows
    @Override
    public void update(Player player) {
        CreativePlayer creativePlayer = new CreativePlayer().load(creativePlayerDbAPI.load(player.getUniqueId().toString()).get());
        player.setPlayerListName(creativePlayer.getFormattedName());
    }

    @Override
    public void addHeaderLine(TextComponent text) {
        header.add(new BaseComponent[]{withColor(text.toPlainText())});
    }

    @Override
    public void addFooterLine(TextComponent text) {
        footer.add(new BaseComponent[]{withColor(text.toPlainText())});
    }

    private BaseComponent withColor(String text) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', text));
    }
}
