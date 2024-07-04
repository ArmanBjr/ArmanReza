package com.example.demo1;

import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    public User user;
    @FXML
    private Tab HomePageProfile;
    @FXML
    private Label timeLabel;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @FXML
    private Label HomePageUserName;
    @FXML
    private Button Exchange;

    @FXML
    private Button HomePage;

    @FXML
    private Button Profile;

    @FXML
    private Button SignOut;

    @FXML
    private Button Swap;

    @FXML
    private Button Token;

    @FXML
    private Button Transfer;

    @FXML
    private ImageView ProfileImage;

    @FXML
    void OnSignOutButtonClicked(ActionEvent event) throws IOException {
        SignOut.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("SignView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/com/example/demo1/Beauty.css")));
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Label Username;

    public void displayUsername() {
        try {
            Username.setText(GetUser.username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayProfile() {
        try {
            //String uri = "file:" + GetUser.user.getImageUrl();
            //String url = "file:" + GetUser.imageUrl;
            Image image = new Image(GetUser.imageUrl, 200, 150, false, true);
            ProfileImage.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(currentTime.format(timeFormatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startClock();
        displayUsername();
        displayProfile();
    }
}
