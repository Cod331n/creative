package ru.codein.creative.command;

import com.intellectualcrafters.plot.object.Plot;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.codein.creative.Creative;
import ru.codein.creative.chat.MessageSender;
import ru.codein.creative.gui.menu.PlayerListListener;
import ru.codein.creative.gui.menu.PlayerListMenu;
import ru.codein.creative.player.CreativePlayerPlotData;
import ru.codein.creative.util.FileHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class PCheck implements CommandExecutor {
    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Plot plot = Creative.getPlugin().getPlotAPI().getPlot(player);
            Date date = new Date(System.currentTimeMillis());
            String formattedDate = new SimpleDateFormat("HH:mm:ss dd.MM.yy").format(date);

            if ("done".equals(args[0])) {
                CreativePlayerPlotData creativePlayerPlotData = new CreativePlayerPlotData(player.getUniqueId().toString(), player.getWorld().getName(), plot.getId(), formattedDate, new ArrayList<>());

                if (plot.getOwners().stream().anyMatch(uuid -> uuid.equals(player.getUniqueId()))) {
                    if (!FileHelper.JSON_DIRECTORY.exists()) {
                        FileHelper.JSON_DIRECTORY.mkdir();
                    }

                    FileHelper.toJson(player.getName(), FileHelper.JSON_DIRECTORY, creativePlayerPlotData);
                    MessageSender.sendFine(player, "Плот отправлен на проверку");
                } else {
                    MessageSender.sendError(player, "Это не ваш плот");
                }

            } else if ("check".equals(args[0])) {
                List<File> plotsToCheckFiles = FileHelper.getFilesInDir(FileHelper.JSON_DIRECTORY);
                List<CreativePlayerPlotData> plotsToCheck = new ArrayList<>(plotsToCheckFiles.size());
                plotsToCheckFiles.forEach(file -> plotsToCheck.add(FileHelper.fromJson(file)));

                PlayerListMenu playerListMenu = new PlayerListMenu();
                boolean isCompleted = playerListMenu.open(player, plotsToCheck);
                if (isCompleted) {
                    PlayerListListener playerListListener = new PlayerListListener(playerListMenu.getPages(), playerListMenu.getPlotDataHashMap());
                    playerListListener.register();
                }
            }

            return false;
        }

        return false;
    }
}
