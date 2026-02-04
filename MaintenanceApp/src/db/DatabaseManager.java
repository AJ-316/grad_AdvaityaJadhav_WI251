package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class DatabaseManager {

    private static final Map<String, PreparedStatement> psMap = new HashMap<>();
    private static Connection connection;

    public static void establishConnection(String driver, String user, String pswd) {
        try {
            disconnect();

            connection = DriverManager.getConnection(driver, user, pswd);

            System.out.println("\u001b[33mConnected to Database: " + connection.getMetaData().getURL() + "\u001b[0m");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnect() {
        if (connection == null) return;

        try {
            for (PreparedStatement ps : psMap.values())
                ps.close();

            connection.close();
            System.out.println("\u001b[31mDisconnected from Database: " + connection.getMetaData().getURL() + "\u001b[0m");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getPreparedStatement(String key, Object... values) throws Exception {
        PreparedStatement ps = psMap.get(key);
        if (ps == null) {
            throw new NoSuchElementException("No such statement: " + key);
        }

        for (int i = 0; i < values.length; i++) {
            ps.setObject(i + 1, values[i]);
        }
        return ps;
    }

    public static void addPreparedStatement(String key, String query) {
        try {
            psMap.put(key, connection.prepareStatement(query));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
