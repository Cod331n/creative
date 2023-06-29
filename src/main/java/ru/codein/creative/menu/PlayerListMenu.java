package ru.codein.creative.menu;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import ru.codein.creative.Creative;
import ru.codein.creative.api.v1.RankAPI;
import ru.codein.creative.criteria.PlotCriteria;
import ru.codein.creative.player.CreativePlayerPlotData;
import ru.codein.creative.rank.Rank;
import ru.codein.creative.util.ItemMetaUtils;
import ru.codein.creative.util.MathUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Getter
public class PlayerListMenu {
    private final int PER_PAGE = 28;
    private final List<Inventory> pages = new ArrayList<>();
    private final HashMap<Integer, CreativePlayerPlotData> plotDataHashMap = new HashMap<>();
    private boolean isCompleted = false;

    @SneakyThrows
    @SuppressWarnings("StringBufferReplaceableByString")
    public boolean open(Player player, List<CreativePlayerPlotData> dataList) {

        CompletableFuture<Boolean> completionFuture = CompletableFuture.supplyAsync(() -> {
            int amountPlots = dataList.size();
            // количество страниц меню
            int amountPages = (int) Math.ceil((double) amountPlots / PER_PAGE);
            // предмет пустого слота
            ItemStack item = new ItemStack(Material.FIREWORK_CHARGE);

            // итерирование по страницам
            for (int i = 0; i <= amountPages; i++) {

                // создание меню и кнопок каждой страницы
                String str = new StringBuilder("список плотов, стр. ").append(i + 1).toString();
                ItemStack buttonRight = new ItemStack(Material.STAINED_GLASS_PANE);
                ItemStack buttonLeft = new ItemStack(Material.STAINED_GLASS_PANE);
                buttonRight.setItemMeta(ItemMetaUtils.setDisplayName(item, "Вперед"));
                buttonLeft.setItemMeta(ItemMetaUtils.setDisplayName(item, "Назад"));

                Inventory menu = new MenuBuilder(player, str, 54, Creative.getPlugin())
                        .set(PositionWrapper.PositionBigChest.BOTTOM_RIGHT, buttonRight)
                        .set(PositionWrapper.PositionBigChest.BOTTOM_LEFT, buttonLeft)
                        .makeAreaOfPoints(item)
                        .build();

                // получение скина игрока (если лицензия)
                // и установка его на голову
                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1);
                ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

                // итерирование по пустым слотам
                int menuIndex = 0;
                for (int j = 0; j < menu.getSize(); j++) {
                    // если список плотов уже пуст, то заканчиваем итерацию
                    if (dataList.size() == 0 || dataList.get(menuIndex) == null) {
                        break;
                    }

                    // проверка, если предмет инвентаря пустой слот
                    if (item.equals(menu.getItem(j))) {
                        CreativePlayerPlotData creativePlayerPlotData = dataList.get(menuIndex);

                        // получение средней оценки игроков
                        List<PlotCriteria> plotCriteriaList = creativePlayerPlotData.getPlotCriteriaList();
                        List<Double> creativityList = new ArrayList<>();
                        List<Double> compositionList = new ArrayList<>();
                        List<Double> realizationList = new ArrayList<>();
                        plotCriteriaList.forEach(element -> {
                            creativityList.add(element.getCreativity());
                            compositionList.add(element.getComposition());
                            realizationList.add(element.getRealization());
                        });

                        double avgCreativity = MathUtils.getAvgDouble(creativityList);
                        double avgComposition = MathUtils.getAvgDouble(compositionList);
                        double avgRealization = MathUtils.getAvgDouble(realizationList);

                        // создание описания для элемента меню
                        List<String> lore = new ArrayList<>();
                        Player plotOwner = Bukkit.getPlayer(UUID.fromString(creativePlayerPlotData.getUuid()));
                        RankAPI rankAPI = Creative.getPlugin().getApiService().getRankAPI();
                        Rank rank = rankAPI.getRank(player);
                        Rank nextRank = rankAPI.getNextRank(rank);

                        lore.add(" ");
                        lore.add(ChatColor.WHITE + "Плот для повышения ранга до " + ChatColor.translateAlternateColorCodes('&', nextRank.getPrefixColor() + nextRank.getName()));
                        lore.add(" ");
                        lore.add(ChatColor.GOLD + "Средняя оценка");
                        lore.add(ChatColor.WHITE + "Креативность: " + ChatColor.GRAY + avgCreativity);
                        lore.add(ChatColor.WHITE + "Композиция: " + ChatColor.GRAY + avgComposition);
                        lore.add(ChatColor.WHITE + "Реализация: " + ChatColor.GRAY + avgRealization);
                        lore.add(ChatColor.WHITE + "Оцененили " + ChatColor.GRAY + plotCriteriaList.size());
                        lore.add(" ");
                        lore.add(ChatColor.WHITE + "Дата выставления: " + ChatColor.GRAY + creativePlayerPlotData.getFormattedDate());
                        lore.add(" ");
                        lore.add(ChatColor.WHITE + "Нажмите" + ChatColor.GOLD + " ПКМ " + ChatColor.WHITE + "чтобы переместиться для проверки.");
                        lore.add(ChatColor.WHITE + "Нажмите" + ChatColor.GOLD + " ЛКМ " + ChatColor.WHITE + "чтобы оценить плот по критериям.");

                        skullMeta.setLocalizedName("Плот игрока " + ChatColor.translateAlternateColorCodes('&', rank.getPrefixColor() + rank.getPrefix() + " &f" + plotOwner.getName()));
                        skullMeta.setOwningPlayer(plotOwner);
                        playerHead.setItemMeta(skullMeta);
                        playerHead.setItemMeta(ItemMetaUtils.setLore(playerHead, lore));

                        // установка на подходящий индекс голову
                        menu.setItem(j, playerHead);
                        // добавление айди в инвентаре и данных о плоте в мап
                        plotDataHashMap.put(j, dataList.get(menuIndex));
                        // удаление первого элемента
                        dataList.remove(menuIndex);
                    }
                }

                // добавление страницы готового меню в список
                pages.add(menu);

            }

            // открытие первой страницы игроку
            player.openInventory(pages.get(0));

            return true;
        });

        try {
            // ожидание завершения и получение результата
            isCompleted = completionFuture.join();
        } catch (CompletionException e) {
            // обработка исключения, если задача завершилась с ошибкой
            isCompleted = false;
            e.printStackTrace();
        }

        return isCompleted;
    }
}