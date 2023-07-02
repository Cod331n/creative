package ru.codein.creative.permission;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.PermissionAPI;

import java.util.concurrent.CompletableFuture;

public class PermissionService implements PermissionAPI {

    @Override
    @SneakyThrows
    public boolean addPermission(Player player, String perm) {
        if (player != null && player.getEffectivePermissions() != null) {
            PermissionAttachment attachment = player.addAttachment(Creative.getPlugin());
            attachment.setPermission(perm, true);
            reloadPermissions(player);

            return true;
        }

        return false;
    }

    @Override
    @SneakyThrows
    public boolean removePermission(Player player, String perm) {
        if (player != null && player.getEffectivePermissions() != null) {
            return CompletableFuture.supplyAsync(() -> {
                player.getEffectivePermissions().stream()
                        .filter(attachment -> attachment.getPermission().equalsIgnoreCase(perm))
                        .forEach(attachment -> player.removeAttachment(attachment.getAttachment()));
                reloadPermissions(player);

                return true;

            }).get();
        }

        return false;
    }

    @Override
    public boolean reloadPermissions(Player player) {
        player.recalculatePermissions();

        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    @SneakyThrows
    public boolean removePermissions(Player player) {
        if (player != null && player.getEffectivePermissions() != null) {
            return CompletableFuture.supplyAsync(() -> {
                player.getEffectivePermissions().forEach(permissionAttachmentInfo -> removePermission(player, permissionAttachmentInfo.getPermission()));
                reloadPermissions(player);

                return true;
            }).get();
        }

        return false;
    }
}
