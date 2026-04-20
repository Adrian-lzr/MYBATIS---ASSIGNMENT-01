package com.example.student;

import org.h2.tools.RunScript;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.Objects;
import java.util.regex.Pattern;

public final class DatabaseInitializer {
    private static final Pattern MYSQL_DATABASE_NAME = Pattern.compile("[A-Za-z0-9_]+");

    private DatabaseInitializer() {
    }

    public static void initialize() {
        try {
            Class.forName(DbConfig.jdbcDriver());
            if ("mysql".equals(DbConfig.dbType())) {
                ensureMySqlDatabaseExists();
            }
            try (Connection connection = DriverManager.getConnection(
                    DbConfig.jdbcUrl(),
                    DbConfig.jdbcUsername(),
                    DbConfig.jdbcPassword()
            )) {
                try (InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream("schema.sql");
                     Reader reader = new InputStreamReader(
                             Objects.requireNonNull(inputStream, "schema.sql not found"),
                             StandardCharsets.UTF_8
                     )) {
                    RunScript.execute(connection, reader);
                }

                if (isTableEmpty(connection)) {
                    seed(connection);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize database", e);
        }
    }

    private static void ensureMySqlDatabaseExists() throws Exception {
        String jdbcUrl = DbConfig.jdbcUrl();
        String databaseName = extractDatabaseName(jdbcUrl);
        String serverUrl = withoutDatabaseName(jdbcUrl);

        if (!MYSQL_DATABASE_NAME.matcher(databaseName).matches()) {
            throw new IllegalStateException("Unsafe MySQL database name: " + databaseName);
        }

        try (Connection connection = DriverManager.getConnection(
                serverUrl,
                DbConfig.jdbcUsername(),
                DbConfig.jdbcPassword()
        ); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + databaseName + "`");
        }
    }

    private static String withoutDatabaseName(String jdbcUrl) {
        int schemeEnd = "jdbc:mysql://".length();
        int slashIndex = jdbcUrl.indexOf('/', schemeEnd);
        int queryIndex = jdbcUrl.indexOf('?', slashIndex);
        String prefix = jdbcUrl.substring(0, slashIndex + 1);
        String query = queryIndex >= 0 ? jdbcUrl.substring(queryIndex) : "";
        return prefix + query;
    }

    private static String extractDatabaseName(String jdbcUrl) {
        int schemeEnd = "jdbc:mysql://".length();
        int slashIndex = jdbcUrl.indexOf('/', schemeEnd);
        int queryIndex = jdbcUrl.indexOf('?', slashIndex);
        return queryIndex >= 0 ? jdbcUrl.substring(slashIndex + 1, queryIndex) : jdbcUrl.substring(slashIndex + 1);
    }

    private static boolean isTableEmpty(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM students")) {
            return resultSet.next() && resultSet.getInt(1) == 0;
        }
    }

    private static void seed(Connection connection) throws Exception {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO students (name, address, dob, grade, gender) VALUES (?, ?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, "John");
            preparedStatement.setString(2, "Delhi");
            preparedStatement.setDate(3, Date.valueOf("2000-01-01"));
            preparedStatement.setString(4, "A");
            preparedStatement.setString(5, "MALE");
            preparedStatement.executeUpdate();
        }
    }
}
