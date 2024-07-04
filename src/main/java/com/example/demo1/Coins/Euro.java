package com.example.demo1.Coins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

public class Euro extends Coin{
    private String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
    private String username = "root";
    private String password = "";

    public Euro() {
        // Initialize the highest and lowest prices with default values
        this.highestPrice = Double.MIN_VALUE;
        this.lowestPrice = Double.MAX_VALUE;

        // Schedule the update task to run every minute
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new UpdateTask(), 0, 60000);
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            updatePrices();
        }
    }

    private void updatePrices() {
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            // Get the current time
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // Calculate the time one minute ago
            Timestamp oneMinuteAgo = new Timestamp(currentTime.getTime() - 60000);

            // Query to get the latest price and price one minute ago
            String latestPriceQuery = "SELECT euro FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(latestPriceQuery);
            preparedStatement.setTimestamp(1, currentTime);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double latestPrice = resultSet.getDouble("euro");

                // Update the price
                this.price = latestPrice;

                // Update the highest and lowest prices
                if (latestPrice > this.highestPrice) {
                    this.highestPrice = latestPrice;
                }
                if (latestPrice < this.lowestPrice) {
                    this.lowestPrice = latestPrice;
                }

                // Get the price one minute ago
                preparedStatement.setTimestamp(1, oneMinuteAgo);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    double priceOneMinuteAgo = resultSet.getDouble("euro");

                    // Calculate the change percentage
                    this.changePercentage = ((latestPrice - priceOneMinuteAgo) / priceOneMinuteAgo) * 100;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getPrice() {
        return price;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public static void main(String[] args) {
        Euro euro = new Euro();

        // For testing, print the prices every minute
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Price: " + euro.getPrice());
                System.out.println("Highest Price: " + euro.getHighestPrice());
                System.out.println("Lowest Price: " + euro.getLowestPrice());
                System.out.println("Change Percentage: " + euro.getChangePercentage());
            }
        }, 0, 60000);

        // Keep the program running
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
