package ru.codein.creative.chat;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.codein.creative.Creative;
import ru.codein.creative.criteria.PlotCriteria;
import ru.codein.creative.player.CreativePlayerPlotData;
import ru.codein.creative.util.FileHelper;
import ru.codein.creative.util.MathUtils;

import java.util.UUID;

@SuppressWarnings("deprecation")
@Getter
@Setter
public class ChatCriteriaListener implements Listener {

    private Player player;
    private CreativePlayerPlotData data;
    private double creativity;
    private double composition;
    private double realization;
    private int chatCount;

    public ChatCriteriaListener(Player player, CreativePlayerPlotData data) {
        this.player = player;
        this.data = data;
        this.chatCount = 0;
    }

    public void register() {
        player.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Оцените креативность от &41.0 &fдо &210.0 &fв чате"));
        Bukkit.getPluginManager().registerEvents(this, Creative.getPlugin());
    }

    public void unregister() {
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!player.equals(event.getPlayer())) {
            return;
        }

        Player plotOwner = Bukkit.getPlayer(UUID.fromString(data.getUuid()));
        chatCount++;

        if (chatCount <= 3) {
            String message = event.getMessage();
            double value = 0;

            try {
                value = Double.parseDouble(message);
                if (value > 10 || value < 1) {
                    handleException(event, "Число не соответствует критериям.");
                }
            } catch (NumberFormatException e) {
                handleException(event, "Неверный формат числа. Пожалуйста, введите число с плавающей точкой.");
            }

            switch (chatCount) {
                case 1:
                    creativity = MathUtils.round(value);
                    player.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Оцените композицию от &41.0 &fдо &210.0 &fв чате"));
                    break;
                case 2:
                    composition = MathUtils.round(value);
                    player.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Оцените реализацию от &41.0 &fдо &210.0 &fв чате"));
                    break;
                case 3:
                    realization = MathUtils.round(value);
                    MessageSender.sendFine(player, "Все значения успешно записаны.");

                    data.addCriteria(new PlotCriteria(player.getUniqueId(), creativity, composition, realization));
                    FileHelper.toJson(plotOwner.getName(), FileHelper.JSON_DIRECTORY, data);
                    this.unregister();
                    break;
            }
        } else {
            this.unregister();
        }

        event.setCancelled(true);
    }

    private void handleException(AsyncPlayerChatEvent event, String errorMessage) {
        MessageSender.sendError(event.getPlayer(), errorMessage);
        event.setCancelled(true);
        this.unregister();
    }
}
