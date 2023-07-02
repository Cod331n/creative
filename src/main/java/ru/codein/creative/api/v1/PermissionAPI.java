package ru.codein.creative.api.v1;

import org.bukkit.entity.Player;

public interface PermissionAPI {
    boolean addPermission(Player player, String perm);
    boolean removePermission(Player player, String perm);
    boolean reloadPermissions(Player player);
}
