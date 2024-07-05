package com.example.demo1.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Values {
    public static double getChangePercentage(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT " + column + " FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 2";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            double latestPrice = resultSet.getDouble(column);
            if (resultSet.next()) {
                double previousPrice = resultSet.getDouble(column);
                return ((latestPrice - previousPrice) / previousPrice) * 100;
            }
        }
        return 0.0;
    }
    public static double getLowestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MIN(" + column + ") AS lowest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("lowest");
        }
        return 0.0;
    }
    public static double getHighestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MAX(" + column + ") AS highest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("highest");
        }
        return 0.0;
    }
}
