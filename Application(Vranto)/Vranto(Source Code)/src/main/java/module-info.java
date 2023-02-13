module com.zeeshan.vranto {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;
    requires java.sql;
    requires mysql.connector.j;


    opens com.zeeshan.vranto to javafx.fxml;
    exports com.zeeshan.vranto;
}