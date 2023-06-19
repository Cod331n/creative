package ru.codein.creative.api.v1;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public interface TabAPI {
    void update(Player player);
    void addHeaderLine(TextComponent text);
    void addFooterLine(TextComponent text);
}
