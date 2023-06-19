package ru.codein.creative;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.plotsquared.bukkit.BukkitMain;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.codein.creative.api.v1.APIService;
import ru.codein.creative.api.v1.CreativePlayerDbAPI;
import ru.codein.creative.api.v1.RankAPI;
import ru.codein.creative.api.v1.TabAPI;
import ru.codein.creative.command.LowerRank;
import ru.codein.creative.command.UpRank;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.db.DatabaseConnector;
import ru.codein.creative.listener.PlayerConnection;
import ru.codein.creative.player.CreativePlayerDbImpl;
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
        apiService = new APIService(creativePlayerDbAPI, rankAPI, tabAPI);

        // Регистрация слушателей
        Bukkit.getPluginManager().registerEvents(new PlayerConnection(), this);

        // Регистрация команд
        getCommand("uprank").setExecutor(new UpRank());
        getCommand("lowerrank").setExecutor(new LowerRank());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Creative getPlugin() {
        return plugin;
    }
}
