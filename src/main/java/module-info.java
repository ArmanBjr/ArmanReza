module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.ooxml;
    requires com.opencsv;
    requires commons.math3;
    requires java.mail;


    opens com.example.demo1 to javafx.fxml;
    opens com.example.demo1.Coins to javafx.base;
    exports com.example.demo1;
    exports com.example.demo1.Coins;
    exports com.example.demo1.CoinPages;
    opens com.example.demo1.CoinPages to javafx.fxml;
}