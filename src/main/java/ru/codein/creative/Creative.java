package ru.codein.creative;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.plotsquared.bukkit.BukkitMain;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ru.codein.creative.api.v1.*;
import ru.codein.creative.command.PCheck;
import ru.codein.creative.command.RankDown;
import ru.codein.creative.command.RankUp;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.db.DatabaseConnector;
import ru.codein.creative.permission.PermissionService;
import ru.codein.creative.player.CreativePlayerDbImpl;
import ru.codein.creative.player.PlayerConnectionListener;
import ru.codein.creative.rank.RankImpl;
import ru.codein.creative.tab.TabImpl;

@SuppressWarnings("deprecation")
public final class Creative extends JavaPlugin {
    private static Creative plugin;

    @Getter
    private DatabaseConnector databaseConnector;
    @Getter
    private CreativePlayerDao creativePlayerDao;
    @Getter
    private PlotAPI plotAPI;
    @Getter
    private PS plotSquared;
    @Getter
    private APIService apiService;

    @SneakyThrows
    @Override
    public void onEnable() {
        plugin = this;

        // Подключение к базе данных и создание таблицы
        databaseConnector = new DatabaseConnector();
        creativePlayerDao = databaseConnector.getJdbi().onDemand(CreativePlayerDao.class);
        creativePlayerDao.createTable();

        // Инициализация плагина на плоты, получение его API
        BukkitMain plugin = (BukkitMain) getServer().getPluginManager().getPlugin("PlotSquared");
        plotAPI = new PlotAPI(plugin);
        plotSquared = plotAPI.getPlotSquared();

        // Работа с API используя dependency injection
        CreativePlayerDbAPI creativePlayerDbAPI = new CreativePlayerDbImpl(databaseConnector.getJdbi(), creativePlayerDao);
        RankAPI rankAPI = new RankImpl(creativePlayerDbAPI);
        TabAPI tabAPI = new TabImpl(creativePlayerDbAPI);
        PermissionAPI permissionAPI = new PermissionService();
        apiService = new APIService(creativePlayerDbAPI, rankAPI, tabAPI, permissionAPI);

        // Регистрация слушателей
        PlayerConnectionListener playerConnectionListener = new PlayerConnectionListener();
        playerConnectionListener.register();

        // Регистрация команд
        getCommand("rankup").setExecutor(new RankUp());
        getCommand("rankdown").setExecutor(new RankDown());
        getCommand("pcheck").setExecutor(new PCheck());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Creative getPlugin() {
        return plugin;
    }
}
