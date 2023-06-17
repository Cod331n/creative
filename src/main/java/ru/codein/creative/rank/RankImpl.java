package ru.codein.creative.rank;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import ru.codein.creative.api.v1.APIService;
import ru.codein.creative.api.v1.RankAPI;
import ru.codein.creative.player.CreativePlayerData;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;

public class RankImpl implements RankAPI {
    private final CreativePlayerDbAPI creativePlayerDbAPI = new APIService().getCreativePlayerDbAPI();

    @Override
    public void setRank(Player player, Rank rank) {
        creativePlayerDbAPI.save(new CreativePlayerData(player.getUniqueId().toString(), player.getName(), rank));
    }

    @Override
    public void removeRank(Player player) {
        creativePlayerDbAPI.save(new CreativePlayerData(player.getUniqueId().toString(), player.getName(), Rank.getDefault()));
    }

    @SneakyThrows
    @Override
    public Rank getRank(Player player) {
        return creativePlayerDbAPI.load(player.getUniqueId().toString()).get().getRank();
    }

    @Override
    public void addPermission(Player player, PermissionAttachment permissionAttachment) {

    }

    @Override
    public void removePermission(Player player, PermissionAttachment permissionAttachment) {

    }

    @Override
    public boolean isHigher(Rank rank1, Rank rank2) {
        return rank1.getPriority() > rank2.getPriority();
    }
}
