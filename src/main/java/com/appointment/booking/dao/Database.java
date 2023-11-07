package com.appointment.booking.dao;


import com.appointment.booking.MainApplication;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Location of database
     */
// TODO: 07/11/2023 Replace this with a working solution so that database will be placed under resources
//    final static String location = MainApplication.class.getResource("database/database.db").getPath();
    final static String location = "database.db";
    /**
     * Currently only table needed
     */

    public static boolean isOK() {
        if (!checkDrivers()) return false; //driver errors

        if (!checkConnection()) return false; //can't connect to db

        return checkTables(PersonDAO.tableName, PersonDAO.columns); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean checkTables(String tableName, Map<String, String> columns) {
        String checkTableExists =
                "SELECT count(*) FROM sqlite_master WHERE type='table' AND name=?";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(checkTableExists);
            statement.setString(1, tableName);
            ResultSet rs = statement.executeQuery();
            if (rs.getInt(1) <= 0) {
                // Table doesn't exist, create it
                StringBuilder createTableSQL = new StringBuilder("CREATE TABLE " + tableName + " (");
                for (Map.Entry<String, String> entry : columns.entrySet()) {
                    createTableSQL.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
                }
                createTableSQL.setLength(createTableSQL.length() - 2); // Remove trailing comma and space
                createTableSQL.append(")");
                statement = connection.prepareStatement(createTableSQL.toString());
                statement.executeUpdate();
            }
            return true; // Table exists
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not interact with database");
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }

}