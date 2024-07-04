module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.poi.ooxml;
    requires com.opencsv;
    requires commons.math3;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}