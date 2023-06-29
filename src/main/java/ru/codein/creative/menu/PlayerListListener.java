package ru.codein.creative.menu;

import com.intellectualcrafters.plot.object.Location;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotArea;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import ru.codein.creative.Creative;
import ru.codein.creative.player.CreativePlayerPlotData;

import java.util.HashMap;
import java.util.List;

public class PlayerListListener implements Listener {
    private List<Inventory> pages;
    private HashMap<Integer, CreativePlayerPlotData> plotDataHashMap;

    public void register(List<Inventory> pages, HashMap<Integer, CreativePlayerPlotData> plotDataHashMap) {
        this.pages = pages;
        this.plotDataHashMap = plotDataHashMap;
        Bukkit.getPluginManager().registerEvents(this, Creative.getPlugin());
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteractMenu(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        Player whoClicked = (Player) event.getWhoClicked();

        // проверка, если инвентарь, открытый игроком
        // содержится в списке страниц
        boolean equals = false;
        int pageIndex = 0;
        for (Inventory page : pages) {
            if (event.getClickedInventory().equals(page)) {
                equals = true;
                pageIndex = pages.indexOf(page);
            }
        }

        if (equals) {
            int id = event.getSlot();
            if (plotDataHashMap.containsKey(id) && event.getClick().isRightClick()) {
                CreativePlayerPlotData creativePlayerPlotData = plotDataHashMap.get(id);
                Plot plot = Creative.getPlugin()
                        .getPlotSquared()
                        .getPlot((PlotArea) Creative.getPlugin().getPlotAPI().getPlotAreas(whoClicked.getWorld()).toArray()[0], creativePlayerPlotData.getPlotId());

                // преобразование из локации из плагина plotsquared в локацию bukkit
                plot.getTop().setZ(plot.getTop().getZ() / 3);
                Location locationRaw = plot.getTop();
                org.bukkit.Location location = new org.bukkit.Location(Bukkit.getWorld(locationRaw.getWorld()), locationRaw.getX(), locationRaw.getY(), locationRaw.getZ());

                whoClicked.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
            } else if (plotDataHashMap.containsKey(id) && event.getClick().isLeftClick()) {
                //TODO
            } else if (event.getSlot() == PositionWrapper.PositionBigChest.BOTTOM_RIGHT.getSlotId()) {
                if (pages.size() <= pageIndex + 1) {
                    event.setCancelled(true);
                    return;
                }
                whoClicked.openInventory(pages.get(pageIndex + 1));
                event.setCancelled(true);
            } else if (event.getSlot() == PositionWrapper.PositionBigChest.BOTTOM_LEFT.getSlotId()) {
                if (pageIndex < 0) {
                    event.setCancelled(true);
                    return;
                }
                whoClicked.openInventory(pages.get(pageIndex - 1));
                event.setCancelled(true);
            }
        }
        event.setCancelled(true);
    }
}
