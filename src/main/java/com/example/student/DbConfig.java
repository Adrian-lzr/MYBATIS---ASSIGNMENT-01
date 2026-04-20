package com.example.student;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.Locale;

public final class DbConfig {
    private static final Properties PROPERTIES = load();
    private static final String PROFILE = PROPERTIES.getProperty("db.type", "h2").trim().toLowerCase(Locale.ROOT);

    private DbConfig() {
    }

    private static Properties load() {
        try (InputStream inputStream = DbConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(inputStream, "db.properties not found"));
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load db.properties", e);
        }
    }

    private static String require(String suffix) {
        String value = PROPERTIES.getProperty(PROFILE + "." + suffix);
        if (value == null) {
            throw new IllegalStateException("Missing db.properties entry: " + PROFILE + "." + suffix);
        }
        return value.trim();
    }

    public static String dbType() {
        return PROFILE;
    }

    public static String jdbcDriver() {
        return require("jdbc.driver");
    }

    public static String jdbcUrl() {
        return require("jdbc.url");
    }

    public static String jdbcUsername() {
        return require("jdbc.username");
    }

    public static String jdbcPassword() {
        return require("jdbc.password");
    }

    public static Properties myBatisProperties() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver", jdbcDriver());
        properties.setProperty("jdbc.url", jdbcUrl());
        properties.setProperty("jdbc.username", jdbcUsername());
        properties.setProperty("jdbc.password", jdbcPassword());
        return properties;
    }
}
