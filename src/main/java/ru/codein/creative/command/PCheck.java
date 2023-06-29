package ru.codein.creative.command;

import com.intellectualcrafters.plot.object.Plot;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.codein.creative.Creative;
import ru.codein.creative.chat.MessageSender;
import ru.codein.creative.menu.PlayerListListener;
import ru.codein.creative.menu.PlayerListMenu;
import ru.codein.creative.player.CreativePlayerPlotData;
import ru.codein.creative.util.FileHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class PCheck implements CommandExecutor {
    private static final File DIRECTORY = new File("plugins/creative/json/");

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Plot plot = Creative.getPlugin().getPlotAPI().getPlot(player);
            Date date = new Date(System.currentTimeMillis());
            String formattedDate = new SimpleDateFormat("HH:mm:ss dd.MM.yy").format(date);

            if ("done".equals(args[0])) {
                CreativePlayerPlotData creativePlayerPlotData = new CreativePlayerPlotData(player.getUniqueId().toString(), plot.getId(), formattedDate, new ArrayList<>());

                if (plot.getOwners().stream().anyMatch(uuid -> uuid.equals(player.getUniqueId()))) {
                    if (!DIRECTORY.exists()) {
                        DIRECTORY.mkdir();
                    }

                    FileHelper.toJson(player.getName(), DIRECTORY, creativePlayerPlotData);
                    MessageSender.sendFine(player, "Плот отправлен на проверку");
                } else {
                    MessageSender.sendError(player, "Это не ваш плот");
                }

            } else if ("check".equals(args[0])) {
                List<File> plotsToCheckFiles = FileHelper.getFilesInDir(DIRECTORY);
                List<CreativePlayerPlotData> plotsToCheck = new ArrayList<>(plotsToCheckFiles.size());
                plotsToCheckFiles.forEach(file -> plotsToCheck.add(FileHelper.fromJson(file)));

                PlayerListMenu playerListMenu = new PlayerListMenu();
                boolean isCompleted = playerListMenu.open(player, plotsToCheck);
                if (isCompleted) {
                    PlayerListListener playerListListener = new PlayerListListener();
                    playerListListener.register(playerListMenu.getPages(), playerListMenu.getPlotDataHashMap());

                }
            }

            return false;
        }

        return false;
    }
}
