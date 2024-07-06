package com.example.demo1.CurrencyManagement;

import com.example.demo1.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class Order {
    private String username;
    private String DestUsername;
    private int id;
    private String date;
    private String time;
    private String situation;
    private String OrgCurrency;
    private String DstCurrency;
    private double amount;

    public Order(String user, String destUsername, int id, String date, String time, String situation, String OrgCurrency,
                 String DstCurrency, double amount) {
        setUsername(user);
        setDestUsername(destUsername);
        setAmount(amount);
        setDate(date);
        setTime(time);
        setId(id);
        setSituation(situation);
        setDstCurrency(DstCurrency);
        setOrgCurrency(OrgCurrency);
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return this.time;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setSituation(String situation) {
        this.situation = situation;
    }
    public String getSituation() {
        return this.situation;
    }
    public boolean getSituationV() {
        return this.situation.equals("accepted");
    }
    public void setOrgCurrency(String name) {
        this.OrgCurrency = name;
    }
    public String getOrgCurrency() {
        return this.OrgCurrency;
    }
    public void setDstCurrency(String name) {
        this.DstCurrency = name;
    }
    public String getDstCurrency() {
        return this.DstCurrency;
    }
    public void setDestUsername(String username) {
        this.DestUsername = username;
    }
    public String getDestUsername() {
        return this.DestUsername;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return this.amount;
    }
    public static List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE username = ?";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String dstUser = resultSet.getString("DstUser");
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String situation = resultSet.getString("situation");
                String OrgCurrency = resultSet.getString("OriginCurrency");
                String DstCurrency = resultSet.getString("PurposeCurrency");
                double amount = resultSet.getDouble("amount");

                Order order = new Order(user, dstUser, id, date, time, situation, OrgCurrency, DstCurrency, amount);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
    public static List<Order> getOrdersByUsernameAndStatus(String username, boolean isAccepted) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE username = ? AND situation = ?";

        try (Connection connection = DataBase.connectDb();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, isAccepted ? "accepted" : "pending");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String user = resultSet.getString("username");
                String dstUser = resultSet.getString("DstUser");
                int id = resultSet.getInt("id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String situation = resultSet.getString("situation");
                String OrgCurrency = resultSet.getString("OriginCurrency");
                String DstCurrency = resultSet.getString("PurposeCurrency");
                double amount = resultSet.getDouble("amount");

                Order order = new Order(user, dstUser, id, date, time, situation, OrgCurrency, DstCurrency, amount);
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

}
