package com.example.student;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class DbInspector {
    private DbInspector() {
    }

    public static void main(String[] args) {
        try {
            DatabaseInitializer.initialize();
            printDatabaseInfo();
        } catch (Exception e) {
            System.err.println("Failed to inspect database: " + e.getMessage());
            if (isLockedDatabaseError(e)) {
                System.err.println("The database file is currently in use. Close any running Main/IDEA Java process and try again.");
            }
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    private static boolean isLockedDatabaseError(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            String message = current.getMessage();
            if (message != null) {
                String lower = message.toLowerCase(Locale.ROOT);
                if (lower.contains("already in use") || lower.contains("file is locked")) {
                    return true;
                }
            }
            current = current.getCause();
        }
        return false;
    }

    private static void printDatabaseInfo() throws Exception {
        System.out.println("Database type: " + DbConfig.dbType());
        System.out.println("JDBC URL: " + DbConfig.jdbcUrl());
        System.out.println();

        try (Connection connection = DriverManager.getConnection(
                DbConfig.jdbcUrl(),
                DbConfig.jdbcUsername(),
                DbConfig.jdbcPassword()
        )) {
            DatabaseMetaData metaData = connection.getMetaData();
            List<String> tables = listTables(metaData);

            System.out.println("Tables:");
            if (tables.isEmpty()) {
                System.out.println("  (none)");
            } else {
                for (String table : tables) {
                    System.out.println("  - " + table);
                }
            }

            System.out.println();
            if (containsStudentsTable(tables)) {
                printStudentsTable(connection);
            } else {
                System.out.println("students table not found.");
            }
        }
    }

    private static List<String> listTables(DatabaseMetaData metaData) throws Exception {
        Set<String> tables = new HashSet<>();
        try (ResultSet resultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
            while (resultSet.next()) {
                String schema = resultSet.getString("TABLE_SCHEM");
                String table = resultSet.getString("TABLE_NAME");
                if (schema != null && "INFORMATION_SCHEMA".equalsIgnoreCase(schema)) {
                    continue;
                }
                if (table != null && table.toUpperCase(Locale.ROOT).startsWith("SYSTEM_")) {
                    continue;
                }
                if (schema == null || schema.isBlank()) {
                    tables.add(table);
                } else {
                    tables.add(schema + "." + table);
                }
            }
        }
        List<String> sorted = new ArrayList<>(tables);
        sorted.sort(Comparator.naturalOrder());
        return sorted;
    }

    private static boolean containsStudentsTable(List<String> tables) {
        for (String table : tables) {
            String normalized = table.toLowerCase(Locale.ROOT);
            if (normalized.equals("students") || normalized.endsWith(".students")) {
                return true;
            }
        }
        return false;
    }

    private static void printStudentsTable(Connection connection) throws Exception {
        System.out.println("students table:");

        try (Statement statement = connection.createStatement()) {
            try (ResultSet countResult = statement.executeQuery("SELECT COUNT(*) FROM students")) {
                if (countResult.next()) {
                    System.out.println("Row count: " + countResult.getInt(1));
                }
            }

            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT id, name, address, dob, grade, gender FROM students ORDER BY id"
            )) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                StringBuilder header = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    header.append(String.format("%-15s", metaData.getColumnLabel(i)));
                }
                System.out.println(header);

                while (resultSet.next()) {
                    StringBuilder row = new StringBuilder();
                    row.append(String.format("%-15s", resultSet.getObject("id")));
                    row.append(String.format("%-15s", resultSet.getObject("name")));
                    row.append(String.format("%-15s", resultSet.getObject("address")));
                    row.append(String.format("%-15s", resultSet.getObject("dob")));
                    row.append(String.format("%-15s", resultSet.getObject("grade")));
                    row.append(String.format("%-15s", resultSet.getObject("gender")));
                    System.out.println(row);
                }
            }
        }
    }
}
