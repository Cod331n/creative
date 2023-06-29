package ru.codein.creative.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemMetaUtils {
    public static ItemMeta setDisplayName(ItemStack item, String name){
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        return meta;
    }
    public static ItemMeta setLore(ItemStack item, List<String> lore){
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        return meta;
    }

    public static ItemMeta setLore(ItemStack item, String lore){
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Collections.singletonList(lore));
        return meta;
    }

    public static List<String> createLoreLines(String ... lines) {
        return new ArrayList<>(Arrays.asList(lines));
    }
}
