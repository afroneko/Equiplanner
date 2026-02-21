package com.lisa.equiplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String url = "jdbc:mysql://localhost:3306/equiplanner_db";
    private final String user = "root";
    private final String password = "";

    public Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}