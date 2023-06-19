package ru.codein.creative.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DatabaseConnector {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Jdbi getJdbi() {
        CompletableFuture<Jdbi> result =  CompletableFuture.supplyAsync(() -> {
            String url = getProperty("DB_URL");
            String username = getProperty("DB_USERNAME");
            String password = getProperty("DB_PASSWORD");

            File databaseFile = new File("plugins/db/database.db");

            // Создание директории, если она не существует
            databaseFile.getParentFile().mkdirs();

            Jdbi jdbi = Jdbi.create(url, username, password);
            jdbi.installPlugin(new SqlObjectPlugin());

            return jdbi;
        });

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getProperty(String propertyName) {
        String property = null;
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            properties.load(inputStream);
            property = properties.getProperty(propertyName);
        } catch (IOException e) {
            System.out.println("couldn't load database property file");
        }
        return property;
    }
}
