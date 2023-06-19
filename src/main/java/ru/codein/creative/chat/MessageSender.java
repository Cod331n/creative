package ru.codein.creative.chat;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.codein.creative.player.CreativePlayer;

/**
 * Класс, предоставляющий утилиты для отправки сообщений в чат.
 */
@UtilityClass
public class MessageSender {

    /**
     * Отправляет сообщение в глобальный чат с указанным именем и сообщением.
     *
     * @param creativePlayer игрок который отправит сообщение
     * @param message        сообщение для отправки
     */
    @SuppressWarnings("StringBufferReplaceableByString")
    public static void sendMessage(CreativePlayer creativePlayer, String message) {
        String format = new StringBuilder()
                .append(creativePlayer.getFormattedName())
                .append(" &4")
                .append(message).toString();

        Bukkit.broadcastMessage(withColor(format));
    }

    /**
     * Отправляет сообщение в глобальный чат.
     *
     * @param message сообщение для отправки
     */
    public static void sendMessage(String message) {
        Bukkit.broadcastMessage(message);
    }

    /**
     * Отправляет сообщение игроку об успехе.
     *
     * @param player  игрок, которому будет отправлено сообщение
     * @param message сообщение для отправки
     */
    public static void sendFine(Player player, String message) {
        sendMessage("&2[+]", message, player);
    }

    /**
     * Отправляет предупреждение игроку.
     *
     * @param player  игрок, которому будет отправлено предупреждение
     * @param message сообщение предупреждения
     */
    public static void sendWarning(Player player, String message) {
        sendMessage("&e[!]", message, player);
    }

    /**
     * Отправляет сообщение о ошибке игроку.
     *
     * @param player  игрок, которому будет отправлено сообщение об ошибке
     * @param message сообщение об ошибке
     */
    public static void sendError(Player player, String message) {
        sendMessage("&4[-]", message, player);
    }

    private static void sendMessage(String prefix, String message, Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + message));
    }

    private static String withColor(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}







