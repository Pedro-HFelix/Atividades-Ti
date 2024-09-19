package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected Connection connection;
    private final String url = "jdbc:postgresql://ti-teste.postgres.database.azure.com:5432/postgres";
    private final String user = "AdminBanco";
    private final String password = "Felix@3011";

    public void DAO () {
        this.connection = null;
    }

    public void openConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = DriverManager.getConnection(url, user, password);
        }
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }
}
