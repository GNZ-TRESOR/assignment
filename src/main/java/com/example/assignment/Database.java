package com.example.assignment;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        String dbdriver = "com.mysql.cj.jdbc.Driver";
        String dburl = "jdbc:mysql://localhost:3306/";
        String dbName = "patients";
        String user = "root";
        String password = "Gnz-tresor2006";

        Class.forName(dbdriver);
        return DriverManager.getConnection(dburl + dbName, user, password);
    }
    }
