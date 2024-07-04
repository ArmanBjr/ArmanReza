package com.example.demo1;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/exchange","root","");
            return connect;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
