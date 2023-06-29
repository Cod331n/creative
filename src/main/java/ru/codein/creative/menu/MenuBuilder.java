package ru.codein.creative.menu;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.codein.creative.Creative;
import ru.codein.creative.util.ItemMetaUtils;

@Getter
public class MenuBuilder {
    private Player owner;
    private String name;
    private int size;
    private Creative plugin;
    private Inventory current;

    public MenuBuilder(Player owner, String name, int size, Creative plugin) {
        this.owner = owner;
        this.name = name;
        this.size = size;
        this.plugin = plugin;

        current = Bukkit.createInventory(owner, size, name);
    }

    public MenuBuilder set(int slotId, ItemStack item) {
        current.setItem(slotId, item);
        return this;
    }

    public MenuBuilder set(PositionWrapper.PositionBigChest position, ItemStack item) {
        current.setItem(position.getSlotId(), item);
        return this;
    }

    public MenuBuilder set(PositionWrapper.PositionSmallChest position, ItemStack item) {
        current.setItem(position.getSlotId(), item);
        return this;
    }

    public MenuBuilder makeAreaOfPoints(ItemStack item) {
        if (size != 54) {
            throw new RuntimeException("THIS METHOD SUPPORTS ONLY SIZE OF 54 (BIGCHEST)");
        }

        item.setItemMeta(ItemMetaUtils.setDisplayName(item, "пустой слот"));
        item.setItemMeta(ItemMetaUtils.setLore(item, " "));
        for (int i = 10; i <= 16; i++) {
            current.setItem(i, item);
        }
        for (int i = 19; i <= 25; i++) {
            current.setItem(i, item);
        }
        for (int i = 28; i <= 34; i++) {
            current.setItem(i, item);
        }
        for (int i = 37; i <= 43; i++) {
            current.setItem(i, item);
        }

        return this;
    }


    public Inventory build() {
        return current;
    }


}
