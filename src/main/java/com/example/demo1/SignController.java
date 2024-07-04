package com.example.demo1;

import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import com.example.demo1.User.UserDAO;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.demo1.User.UserDAO;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;

public class SignController {
    private boolean isCaptchaVerified;
    @FXML
    private RadioButton SignInCaptchaButton;

    @FXML
    private Button SignInForgotPasswordButton;

    @FXML
    private AnchorPane SignInPage;

    @FXML
    private PasswordField SignInPasswordText;

    @FXML
    private Button SignInSignUpButton;

    @FXML
    private TextField SignInUsernameText;

    @FXML
    private Button SignInsigningbutton;

    @FXML
    private TextField SignUpEmailText;
    private String imageUrl;

    @FXML
    private ImageView SignUpProfileImage;


    @FXML
    private TextField SignUpFirstNameText;

    @FXML
    private TextField SignUpLastNameText;

    @FXML
    private AnchorPane SignUpPage;

    @FXML
    private PasswordField SignUpPasswordText;

    @FXML
    private TextField SignUpPhoneNumberText;

    @FXML
    private PasswordField SignUpRepeatPasswordText;

    @FXML
    private TextField SignUpUsernameText;

    @FXML
    private Hyperlink SignUpalreadyhaveanaccountButton;

    @FXML
    private Button SignUpsignupbutton;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    public void switchForm(ActionEvent event) {
        if (event.getSource() == SignInSignUpButton) {
            SignInPage.setVisible(false);
            SignUpPage.setVisible(true);
        } else if (event.getSource() == SignUpalreadyhaveanaccountButton) {
            SignInPage.setVisible(true);
            SignUpPage.setVisible(false);
        }
    }
    public void onSignUpsignupbuttonClicked(ActionEvent event) {
        String sql = "INSERT INTO info (email, username, password, FirstName, LastName, phoneNumber, Image) VALUEs (?,?,?,?,?,?,?)";
        connect = DataBase.connectDb();
        try{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            prepare = connect.prepareStatement(sql);
            UserDAO.getAllUsers();
            for (User user: UserDAO.users) {
                if (user.getUsername().equals(SignInUsernameText.getText())) {
                    alert.setContentText("User Already exists!");
                    alert.showAndWait();
                }
            }
            prepare.setString(1, SignUpEmailText.getText());
            prepare.setString(2, SignUpUsernameText.getText());
            prepare.setString(3, SignUpPasswordText.getText());
            prepare.setString(4, SignUpFirstNameText.getText());
            prepare.setString(5, SignUpLastNameText.getText());
            prepare.setString(6, SignUpPhoneNumberText.getText());
            prepare.setString(7, this.imageUrl);
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error message!");
            alert1.setHeaderText(null);
            if (!Validator.isValidEmail(SignUpEmailText.getText())) {
                alert1.setContentText("invalid email!");
                alert1.showAndWait();
            }
            else if (!(Validator.isValidName(SignUpFirstNameText.getText()) ||
                    Validator.isValidName(SignUpLastNameText.getText()))){
                alert1.setContentText("invalid first or last name!");
                alert1.showAndWait();
            }
            else if (!Validator.isValidPassword(SignUpPasswordText.getText())) {
                alert1.setContentText("invalid password!");
                alert1.showAndWait();
            }
            else if (!Validator.isValidPhoneNumber(SignUpPhoneNumberText.getText())) {
                alert1.setContentText("invalid phone number!");
                alert1.showAndWait();
            }
            else if (!SignUpPasswordText.getText().equals(SignUpRepeatPasswordText.getText())) {
                alert1.setContentText("password and its repeat are not the same!");
                alert1.showAndWait();
            }
            else {
                prepare.execute();
                alert.setContentText("successfully signed up");
                alert.showAndWait();
                SignUpEmailText.setText("");
                SignUpUsernameText.setText("");
                SignUpPasswordText.setText("");
                SignUpFirstNameText.setText("");
                SignUpLastNameText.setText("");
                SignUpPhoneNumberText.setText("");
                SignUpRepeatPasswordText.setText("");
                SignUpPage.setVisible(false);
                SignInPage.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void onSignInButtonClicked(ActionEvent event) {
        System.out.println("salam");
        String sql = "SELECT * FROM info WHERE username = ? and password = ?";
        connect = DataBase.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, SignInUsernameText.getText());
            prepare.setString(2, SignInPasswordText.getText());
            result = prepare.executeQuery();
            Alert alert;
            if (SignInUsernameText.getText().isEmpty() || SignInPasswordText.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("wrong input! try again!");
                alert.showAndWait();
            }
            else {
                if (!isCaptchaVerified && false) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please verify the CAPTCHA");
                    alert.showAndWait();
                }
                else if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    try (Socket socket = new Socket("localhost", 12345);
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        out.println(SignInUsernameText.getText());
                        out.println(SignInPasswordText.getText());
                        String response = in.readLine();
                        if ("Login successful".equals(response)) {
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("successfully Login!");
                            alert.showAndWait();
                            GetUser.username = SignInUsernameText.getText();
                            SignInsigningbutton.getScene().getWindow().hide();
                            Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            UserDAO.loadUsersFromDatabase();
                            User user = UserDAO.userFinder1(SignInUsernameText.getText(), SignInPasswordText.getText());
                            GetUser.user = user;
                            System.out.println(user.getImageUrl());
                            GetUser.imageUrl = user.getImageUrl();
                            System.out.println(user.getImageUrl());
                            stage.setScene(scene);
                            stage.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username/password");
                    alert.showAndWait();
                }
                }
            } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void onSignUpButtonClicked(ActionEvent event) {

    }

    public void importImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Import Image File");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*png", "*jpg"));
        Stage stage = (Stage) SignUpPasswordText.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            Image image = new Image(file.toURI().toString(), 46, 45, false, true);
            SignUpProfileImage.setImage(image);
            imageUrl = file.toURI().toString();
        }
    }
    public void setSignInCaptchaButtonClicked(ActionEvent event) throws InterruptedException {
        SignInCaptchaButton.setSelected(false);
        CaptchaApp captchaApp = new CaptchaApp();
        boolean isOk;
        isOk = captchaApp.showCaptcha();
        isCaptchaVerified = isOk;
        SignInCaptchaButton.setSelected(isOk);
    }
}