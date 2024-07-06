package com.example.demo1;

import com.example.demo1.Coins.CoinInfo;
import com.example.demo1.User.GetUser;
import com.example.demo1.User.User;
import com.example.demo1.Utilities.FixedSizeList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
    private MenuItem Euro;

    @FXML
    private Button SignOut;

    @FXML
    private Button Swap;

    @FXML
    private Button Token;

    @FXML
    private Button Transfer;

    @FXML
    private TableView<CoinInfo> CoinInfoTable;

    @FXML
    private TableColumn<CoinInfo, String> TableChangeColumn;

    @FXML
    private TableColumn<CoinInfo, String> TableHighestPriceColumn;

    @FXML
    private TableColumn<CoinInfo, String> TableLowestColumn;

    @FXML
    private TableColumn<CoinInfo, String> TableMarketColumn;

    @FXML
    private TableColumn<CoinInfo, String> TablePriceColumn;
    @FXML
    private ImageView ProfileImage;

    @FXML
    private MenuItem Toman;
    @FXML
    private MenuItem Usd;

    @FXML
    private Label ProfileUsername;

    @FXML
    private Label ProfileUserUsername;

    @FXML
    private Label ProfileFirstName;

    @FXML
    private Label ProfileUserFirstName;

    @FXML
    private Label ProfileLastName;

    @FXML
    private Label ProfileUserLastName;

    @FXML
    private ImageView ProfileUserImage;

    @FXML
    private Label ProfilePhoneNumber;

    @FXML
    private Label ProfileEmail;

    @FXML
    private Label ProfileUserPhoneNumber;

    @FXML
    private Label ProfileUserEmail;

    @FXML
    private Label ProfilePassword;

    @FXML
    private Label ProfileUserPassword;

    @FXML
    private Button History;

    @FXML
    private Button Wallet;

    @FXML
    private Button Edit;
    @FXML
    private AnchorPane ProfilePage;
    @FXML
    private AnchorPane HomePageAnchor;
    @FXML
    private AnchorPane ProfileAnchor;
    @FXML
    private Button EditImportPhoto;
    @FXML
    private AnchorPane HomePageProfileEdit;
    @FXML
    private ImageView EditImage;
    public void OnMenuButtonEuroClicked(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EuroChart.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void OnMenuButtonTomanClicked(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TomanChart.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void OnMenuButtonUsdClicked(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UsdChart.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void OnMenuButtonYenClicked(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("YenChart.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void OnMenuButtonGbpClicked(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GbpChart.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setValuesOnProfilePage() {
        ProfileUserUsername.setText(GetUser.username);
        ProfileUserFirstName.setText(GetUser.user.getFirstName());
        ProfileUserLastName.setText(GetUser.user.getLastName());
        ProfileUserPhoneNumber.setText(GetUser.user.getPhoneNumber());
        ProfileUserEmail.setText(GetUser.user.getEmail());
        ProfileUserPassword.setText(GetUser.user.getPassword());
    }
    public void switchForm(ActionEvent e) {
        if (e.getSource() == Profile) {
            Profile.setStyle("-fx-background-color: linear-gradient(to top right, #528560, #70f026)");
            HomePage.setStyle("-fx-background-color: transparent");
            ProfilePage.setVisible(true);
            ProfileAnchor.setVisible(true);
            HomePageAnchor.setVisible(false);
        } else if (e.getSource() == HomePage) {
            ProfilePage.setVisible(false);
            ProfileAnchor.setVisible(false);
            HomePageAnchor.setVisible(true);
            Profile.setStyle("-fx-background-color: transparent");
            HomePage.setStyle("-fx-background-color: linear-gradient(to top right, #528560, #70f026)");
        } else if (e.getSource() == Edit) {

        }
    }

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
    private static final double ROW_HEIGHT = 40.0; // یا هر مقداری که برای ارتفاع هر ردیف مناسب است
    private static final int NUM_ROWS = 6; // تعداد ردیف‌های مورد نظر

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
            if(GetUser.imageUrl != null) {
                Image image = new Image(GetUser.imageUrl, 200, 150, false, true);
                ProfileImage.setImage(image);
            }
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
    private String jdbcURL = "jdbc:mysql://localhost:3306/exchange";
    private String username = "root";
    private String password = "";
    private ObservableList<CoinInfo> coinData = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startClock();
        displayUsername();
        displayProfile();
        setValuesOnProfilePage();
        // Initialize the table columns
        TableMarketColumn.setCellValueFactory(new PropertyValueFactory<>("market"));
        TablePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableHighestPriceColumn.setCellValueFactory(new PropertyValueFactory<>("highestPrice"));
        TableLowestColumn.setCellValueFactory(new PropertyValueFactory<>("lowestPrice"));
        TableChangeColumn.setCellValueFactory(new PropertyValueFactory<>("changePercentage"));

        // Set the data to the table
        CoinInfoTable.setItems(coinData);

        // Schedule the update task to run every minute
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new UpdateTask(), 0, 60000);
    }
    private double getChangePercentage(Connection connection, String column, String date, String time) throws SQLException {
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
    private double getLowestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MIN(" + column + ") AS lowest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("lowest");
        }
        return 0.0;
    }
    private double getHighestPrice(Connection connection, String column, String date, String time) throws SQLException {
        String query = "SELECT MAX(" + column + ") AS highest FROM currency_rates WHERE CONCAT(date, ' ', time) <= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, date + " " + time);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble("highest");
        }
        return 0.0;
    }
    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(() -> displayTable());
        }
    }
    public void displayTable() {
        List<CoinInfo> coins = null;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            // Clear the current data
            coinData.clear();

            // Get the current date and time
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Query to get the latest values up to current date and time
            String query = "SELECT euro, usd, GBP, toman, yen FROM currency_rates WHERE CONCAT(date, ' ', time) <= ? ORDER BY CONCAT(date, ' ', time) DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, formattedDate + " " + formattedTime);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                double euro = resultSet.getDouble("euro");
                double usd = resultSet.getDouble("usd");
                double gpb = resultSet.getDouble("GBP");
                double toman = resultSet.getDouble("toman");
                double yen = resultSet.getDouble("yen");
                // Add rows to the table
                coins = List.of(
                        new CoinInfo("Euro", euro, getHighestPrice(connection, "euro", formattedDate, formattedTime), getLowestPrice(connection, "euro", formattedDate, formattedTime), getChangePercentage(connection, "euro", formattedDate, formattedTime)),
                        new CoinInfo("USD", usd, getHighestPrice(connection, "usd", formattedDate, formattedTime), getLowestPrice(connection, "usd", formattedDate, formattedTime), getChangePercentage(connection, "usd", formattedDate, formattedTime)),
                        new CoinInfo("GPB", gpb, getHighestPrice(connection, "GBP", formattedDate, formattedTime), getLowestPrice(connection, "GBP", formattedDate, formattedTime), getChangePercentage(connection, "GBP", formattedDate, formattedTime)),
                        new CoinInfo("Toman", toman, getHighestPrice(connection, "toman", formattedDate, formattedTime), getLowestPrice(connection, "toman", formattedDate, formattedTime), getChangePercentage(connection, "toman", formattedDate, formattedTime)),
                        new CoinInfo("Yen", yen, getHighestPrice(connection, "yen", formattedDate, formattedTime), getLowestPrice(connection, "yen", formattedDate, formattedTime), getChangePercentage(connection, "yen", formattedDate, formattedTime))
                );
                coinData.add(new CoinInfo("Euro", euro, getHighestPrice(connection, "euro", formattedDate, formattedTime), getLowestPrice(connection, "euro", formattedDate, formattedTime), getChangePercentage(connection, "euro", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("USD", usd, getHighestPrice(connection, "usd", formattedDate, formattedTime), getLowestPrice(connection, "usd", formattedDate, formattedTime), getChangePercentage(connection, "usd", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("GPB", gpb, getHighestPrice(connection, "GBP", formattedDate, formattedTime), getLowestPrice(connection, "GBP", formattedDate, formattedTime), getChangePercentage(connection, "GBP", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("Toman", toman, getHighestPrice(connection, "toman", formattedDate, formattedTime), getLowestPrice(connection, "toman", formattedDate, formattedTime), getChangePercentage(connection, "toman", formattedDate, formattedTime)));
                coinData.add(new CoinInfo("Yen", yen, getHighestPrice(connection, "yen", formattedDate, formattedTime), getLowestPrice(connection, "yen", formattedDate, formattedTime), getChangePercentage(connection, "yen", formattedDate, formattedTime)));
            }
            coinData.setAll(new FixedSizeList<>(coins));
            CoinInfoTable.setFixedCellSize(ROW_HEIGHT);
            CoinInfoTable.prefHeightProperty().bind(Bindings.size(CoinInfoTable.getItems()).multiply(ROW_HEIGHT).add(30).add(ROW_HEIGHT * (NUM_ROWS - coinData.size())));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onEditClicked(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ProfileEdit.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
