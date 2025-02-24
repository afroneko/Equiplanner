package com.lisa.equiplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Equiplanner extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        Scene loginScene = new Scene(root, 800, 550);
        Double center = loginScene.getWidth() / 2;

        ImageView loginImg = new ImageView(
                new Image(String.valueOf(Equiplanner.class.getResource("imgs/dressuurbakMolenzicht.jpg")))
        );
        loginImg.setFitWidth(center);
        loginImg.setFitHeight(loginScene.getHeight());

        BorderPane login = new BorderPane();
        login.setPrefSize(center, loginScene.getHeight());
        login.setLayoutX(center);
        login.setStyle("-fx-background-color: #f4dfcd;");

        root.getChildren().addAll(loginImg, login);
        stage.setTitle("Equiplanner");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}