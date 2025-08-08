package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionSingleton {

    INSTANCE;

    private static final String CONNECTION_ADDRESS = "jdbc:postgresql://localhost:5432/tracker_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(CONNECTION_ADDRESS, USERNAME, PASSWORD);
        }

        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
