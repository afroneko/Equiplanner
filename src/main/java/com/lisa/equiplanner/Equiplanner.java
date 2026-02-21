package com.lisa.equiplanner;

import com.lisa.equiplanner.Controllers.AdminController;
import com.lisa.equiplanner.Models.Admin;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Equiplanner extends Application {
    private AdminController ac;
    @Override
    public void start(Stage stage) {

        Database db = new Database();
        ac = new AdminController(db);

        // --- LEFT IMAGE ---
        ImageView imageView = new ImageView(
                new Image(Equiplanner.class.getResource("/imgs/horseFlank.jpg").toExternalForm())
        );
        imageView.setFitWidth(400);
        imageView.setFitHeight(550);
        imageView.setPreserveRatio(false);

        // --- LOGIN PANE ---
        VBox loginBox = createLoginBox(stage);

        // --- ROOT LAYOUT ---
        HBox root = new HBox(imageView, loginBox);
        Scene scene = new Scene(root, 800, 550);

        stage.setTitle("Equiplanner Login");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createLoginBox(Stage stage) {
        VBox loginBox = new VBox(15);
        loginBox.setPrefWidth(400);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40));
        loginBox.setStyle("-fx-background-color: #f4dfcd;");

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: #450c0f;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Label messageLabel = new Label();

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(200);
        loginButton.setStyle("-fx-background-color: #B22424; -fx-text-fill: white; -fx-font-weight: bold;");

        Button goRegisterButton = new Button("Register");
        goRegisterButton.setStyle("-fx-background-color: #7c3626; -fx-text-fill: white;");

        // Login action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            if (ac.verifyLogin(username, password)) {
                messageLabel.setText("Login successful!");
                // TODO: Load main app window
            } else {
                messageLabel.setText("Invalid username or password");
            }
        });

        // Switch to registration form
        goRegisterButton.setOnAction(e -> stage.getScene().setRoot(createRegisterBox(stage)));

        loginBox.getChildren().addAll(title, usernameField, passwordField, loginButton, goRegisterButton, messageLabel);
        return loginBox;
    }

    private VBox createRegisterBox(Stage stage) {
        VBox registerBox = new VBox(15);
        registerBox.setPrefWidth(400);
        registerBox.setAlignment(Pos.CENTER);
        registerBox.setPadding(new Insets(40));
        registerBox.setStyle("-fx-background-color: #f4dfcd;");

        Label title = new Label("Register");
        title.setStyle("-fx-font-size: 22px; -fx-text-fill: #450c0f;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        Label messageLabel = new Label();

        Button registerButton = new Button("Register");
        registerButton.setPrefWidth(200);
        registerButton.setStyle("-fx-background-color: #B22424; -fx-text-fill: white; -fx-font-weight: bold;");

        Button goLoginButton = new Button("Back to Login");
        goLoginButton.setStyle("-fx-background-color: #7c3626; -fx-text-fill: white;");

        // Register action
        registerButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Username and password required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("Passwords do not match");
                return;
            }

            try {
                Admin admin = new Admin(username, null);
                ac.add(admin, password);
                messageLabel.setText("Registration successful! Return to login.");
            } catch (RuntimeException ex) {
                messageLabel.setText("Error: Username may already exist");
            }
        });

        goLoginButton.setOnAction(e -> stage.getScene().setRoot(createLoginBox(stage)));

        registerBox.getChildren().addAll(title, usernameField, passwordField, confirmPasswordField,
                registerButton, goLoginButton, messageLabel);
        return registerBox;
    }

    public static void main(String[] args) {
        launch();
    }
}