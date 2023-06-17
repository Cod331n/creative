package ru.codein.creative.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnector {
    public Jdbi getJdbi() {
        String url = getProperty("DB_URL");
        String username = getProperty("DB_USERNAME");
        String password = getProperty("DB_PASSWORD");

        return Jdbi.create(url, username, password).installPlugin(new SqlObjectPlugin());
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
