package ru.codein.creative.gui.menu;

import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import ru.codein.creative.Creative;
import ru.codein.creative.chat.ChatCriteriaListener;
import ru.codein.creative.chat.MessageSender;
import ru.codein.creative.criteria.PlotCriteria;
import ru.codein.creative.player.CreativePlayerPlotData;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class PlayerListListener implements Listener {
    private List<Inventory> pages;
    private HashMap<Integer, CreativePlayerPlotData> plotDataHashMap;

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, Creative.getPlugin());
    }

    public void unregister() {
        InventoryClickEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerInteractMenu(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        int pageIndex = getClickedPageIndex(clickedInventory);

        if (pageIndex >= 0) {
            int slot = event.getSlot();
            if (plotDataHashMap.containsKey(slot)) {
                CreativePlayerPlotData creativePlayerPlotData = plotDataHashMap.get(slot);

                if (event.getClick().isRightClick()) {
                    teleportPlayerToPlot(player, creativePlayerPlotData);
                } else if (event.getClick().isLeftClick()) {
                    List<PlotCriteria> plotCriteriaList = creativePlayerPlotData.getPlotCriteriaList();

                    AtomicBoolean isEvaluationGiven = new AtomicBoolean(false);
                    plotCriteriaList.forEach(plotCriteria -> {
                        UUID uuid = plotCriteria.getUuid();
                        if (uuid.equals(player.getUniqueId())) {
                            MessageSender.sendError(player, "Оценка уже отдана.");
                            isEvaluationGiven.set(true);
                            event.setCancelled(true);
                        }
                    });

                    if (isEvaluationGiven.get()) {
                        return;
                    }

                    registerChatCriteriaListener(player, creativePlayerPlotData);
                }

                player.closeInventory();
                event.setCancelled(true);
            } else if (isBottomRightSlot(slot)) {
                openNextPage(player, pageIndex);
                event.setCancelled(true);
            } else if (isBottomLeftSlot(slot)) {
                openPreviousPage(player, pageIndex);
                event.setCancelled(true);
            }

            event.setCancelled(true);
        }
    }

    private int getClickedPageIndex(Inventory clickedInventory) {
        for (int i = 0; i < pages.size(); i++) {
            Inventory page = pages.get(i);
            if (clickedInventory.equals(page)) {
                return i;
            }
        }
        return -1;
    }

    private void teleportPlayerToPlot(Player player, CreativePlayerPlotData creativePlayerPlotData) {
        Plot plot = Creative.getPlugin().getPlotSquared().getPlot((PlotArea) Creative.getPlugin().getPlotAPI().getPlotAreas(player.getWorld()).toArray()[0], creativePlayerPlotData.getPlotId());
        Location locationRaw = plot.getCenter();
        org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld( creativePlayerPlotData.getWorldName()), locationRaw.getX(), locationRaw.getY(), locationRaw.getZ());
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    private void registerChatCriteriaListener(Player player, CreativePlayerPlotData creativePlayerPlotData) {
        ChatCriteriaListener chatCriteriaListener = new ChatCriteriaListener(player, creativePlayerPlotData);
        chatCriteriaListener.register();
    }

    private boolean isBottomRightSlot(int slot) {
        return slot == PositionWrapper.PositionBigChest.BOTTOM_RIGHT.getSlotId();
    }

    private boolean isBottomLeftSlot(int slot) {
        return slot == PositionWrapper.PositionBigChest.BOTTOM_LEFT.getSlotId();
    }

    private void openNextPage(Player player, int currentPageIndex) {
        int index = currentPageIndex + 1;
        if (index < pages.size()) {
            player.openInventory(pages.get(index));
        }
    }

    private void openPreviousPage(Player player, int currentPageIndex) {
        int index = currentPageIndex - 1;
        if (index >= 0) {
            player.openInventory(pages.get(index));
        }
    }
}
