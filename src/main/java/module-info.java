module com.lisa.equiplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.lisa.equiplanner to javafx.fxml;
    exports com.lisa.equiplanner;
    opens com.lisa.equiplanner.Models to javafx.base;
}