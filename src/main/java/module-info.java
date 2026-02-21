module com.lisa.equiplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens com.lisa.equiplanner to javafx.fxml;
    opens com.lisa.equiplanner.Models to javafx.base;
    opens com.lisa.equiplanner.Views to javafx.fxml;
    opens com.lisa.equiplanner.Controllers to javafx.fxml;

    exports com.lisa.equiplanner;
}