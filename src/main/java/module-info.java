module com.lisa.equiplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lisa.equiplanner to javafx.fxml;
    exports com.lisa.equiplanner;
}