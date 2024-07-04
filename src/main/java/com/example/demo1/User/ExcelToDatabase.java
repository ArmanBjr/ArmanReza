package com.example.demo1.User;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExcelToDatabase {

    public static void main(String[] args) {
        String csvFilePath = "D:\\university\\AP codes\\testing\\demo1\\src\\main\\resources\\Data\\price.csv";
        String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
        String username = "root";
        String password = "";

        try (FileReader fileReader = new FileReader(csvFilePath);
             CSVReader csvReader = new CSVReader(fileReader);
             Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {

            String[] nextLine;
            String sql = "INSERT INTO currency_rates (date, time, usd, euro, toman, yen, GBP) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            csvReader.readNext(); // Skip header row

            while ((nextLine = csvReader.readNext()) != null) {
                // Check if the line has the correct number of elements
                if (nextLine.length >= 7) {
                    String date = nextLine[0];
                    String time = nextLine[1];
                    double usd = Double.parseDouble(nextLine[2]);
                    double euro = Double.parseDouble(nextLine[3]);
                    double toman = Double.parseDouble(nextLine[4]);
                    double yen = Double.parseDouble(nextLine[5]);
                    double GBP = Double.parseDouble(nextLine[6]);

                    statement.setString(1, date);
                    statement.setString(2, time);
                    statement.setDouble(3, usd);
                    statement.setDouble(4, euro);
                    statement.setDouble(5, toman);
                    statement.setDouble(6, yen);
                    statement.setDouble(7, GBP);

                    statement.addBatch();
                } else {
                    System.err.println("Skipping line: " + String.join(",", nextLine));
                }
            }

            statement.executeBatch();

        } catch (IOException | CsvValidationException | SQLException e) {
            e.printStackTrace();
        }
    }
}