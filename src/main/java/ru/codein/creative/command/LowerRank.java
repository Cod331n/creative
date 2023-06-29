package ru.codein.creative.command;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.APIService;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.api.v1.RankAPI;
import ru.codein.creative.rank.Rank;

public class LowerRank implements CommandExecutor {
    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);
            APIService apiService = Creative.getPlugin().getApiService();
            RankAPI rankAPI = apiService.getRankAPI();
            CreativePlayerDbAPI creativePlayerDbAPI = apiService.getCreativePlayerDbAPI();

            if (player == null) {
                return false;
            }

            Rank currentRank = creativePlayerDbAPI.load(player.getUniqueId().toString()).get().getRank();
            boolean isRankSet = rankAPI.setRank(player, rankAPI.getPreviousRank(currentRank));

            if (isRankSet) {
                apiService.getTabAPI().update(player);
            } else {
                return false;
            }
        }
        return false;
    }
}
