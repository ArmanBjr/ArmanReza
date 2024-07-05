package com.example.demo1;

import com.example.demo1.User.GetUser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

public class ForgetPassController {
    @FXML
    private TextField emailField;
    @FXML
    private Button forgetPassButton;

    @FXML
    private void initialize() {
        forgetPassButton.setOnAction(e -> OnForgetPassClicked());
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < 8) { // Password length
            int index = (int) (rnd.nextFloat() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    public void OnForgetPassClicked() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Email field is empty");
            return;
        }
        try (Connection conn = DataBase.connectDb()) {
            String query = "SELECT * FROM info WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String newPassword = generateRandomPassword();
                updatePasswordInDatabase(email, newPassword);
                sendEmail(email, newPassword);
                GetUser.ForgetEmail = email;
                showAlert(AlertType.INFORMATION, "Success", "A new password has been sent to your email.");
                forgetPassButton.getScene().getWindow().hide();
            } else {
                showAlert(AlertType.ERROR, "Error", "Email not found in database");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "An error occurred");
        }
    }

    private void updatePasswordInDatabase(String email, String newPassword) {
        try (Connection conn = DataBase.connectDb()) {
            String query = "UPDATE info SET password = ? WHERE email = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String email, String newPassword) {
        final String username = "armanbijari3@gmail.com";
        final String password = "fpcivcmuefzzcerf";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("armanbijari3@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset");
            message.setText("Your new password is: " + newPassword);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
