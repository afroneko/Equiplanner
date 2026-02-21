package com.lisa.equiplanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection conn;

    public Database() {
        //'probeer' een connectie te maken met de database, lukt dit niet vang het dan af met een error message
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/equiplanner_db", "root", "");
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            System.out.println("Kan geen verbinding maken met de database");
        }
    }

    //De connectie beschikbaar stellen via een methode
    public Connection getConn() {
        return conn;
    }
}
