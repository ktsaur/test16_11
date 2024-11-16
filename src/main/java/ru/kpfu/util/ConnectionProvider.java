package ru.kpfu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static ConnectionProvider _instance;

    public static ConnectionProvider getInstance()  {
        if (_instance == null) {
            _instance = new ConnectionProvider();
        }
        return _instance;
    }

    private Connection con;

    private ConnectionProvider() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); //подключение драйвера
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java3_books", "root", "ksenia2005");
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getCon() {
        return con;
    }
}
