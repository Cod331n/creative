package ru.codein.creative;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.plotsquared.bukkit.BukkitMain;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.codein.creative.dao.CreativePlayerDao;
import ru.codein.creative.db.DatabaseConnector;
import ru.codein.creative.listener.PlayerConnection;

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

        // Регистрация слушателей
        Bukkit.getPluginManager().registerEvents(new PlayerConnection(), this);

        // Регистрация команд
        

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Creative getPlugin() {
        return plugin;
    }
}
