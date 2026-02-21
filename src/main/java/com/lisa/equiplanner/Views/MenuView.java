package com.lisa.equiplanner.Views;

import com.lisa.equiplanner.Equiplanner;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuView {

    private Equiplanner app;

    public MenuView(Equiplanner app) {
        this.app = app;
    }

    public Scene getScene() {
        VBox vBox = new VBox(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #f4dfcd;");
        vBox.setPrefWidth(800);
        vBox.setPrefHeight(550);

        Button horseOverview = new Button("Paarden overzicht");
        horseOverview.setPrefWidth(200);
        horseOverview.setOnAction(e -> app.showOverview("Paarden overzicht"));

        Button riderOverview = new Button("Ruiter overzicht");
        riderOverview.setPrefWidth(200);
        riderOverview.setOnAction(e -> app.showOverview("Ruiter overzicht"));

        Button instructorOverview = new Button("Instructeur overzicht");
        instructorOverview.setPrefWidth(200);
        instructorOverview.setOnAction(e -> app.showOverview("Instructeur overzicht"));

        Button lessonOverview = new Button("Lessen overzicht");
        lessonOverview.setPrefWidth(200);
        lessonOverview.setOnAction(e -> app.showOverview("Lessen overzicht"));

        vBox.getChildren().addAll(horseOverview, riderOverview, instructorOverview, lessonOverview);

        return new Scene(vBox, 800, 550);
    }
}