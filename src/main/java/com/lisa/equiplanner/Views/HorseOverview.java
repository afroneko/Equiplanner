package com.lisa.equiplanner.Views;

import com.lisa.equiplanner.Controllers.HorseController;
import com.lisa.equiplanner.Models.Horse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HorseOverview {

    private HorseController horseController;
    private ObservableList<Horse> horses;
    private TableView<Horse> horseTable;

    public HorseOverview(HorseController horseController) {
        this.horseController = horseController;
        this.horses = FXCollections.observableArrayList(horseController.getAllHorses());
    }

    private void refreshList() {
        horses.setAll(horseController.getAllHorses());
    }

    public VBox getContent() {
        horseTable = new TableView<>();
        horseTable.setItems(horses);

        TableColumn<Horse, String> nameCol = new TableColumn<>("Naam");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Horse, Integer> ageCol = new TableColumn<>("Leeftijd");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Horse, Boolean> conditionCol = new TableColumn<>("kreupel");
        conditionCol.setCellValueFactory(new PropertyValueFactory<>("lame"));

        TableColumn<Horse, Integer> maxHoursCol = new TableColumn<>("Inzetbaarheid");
        maxHoursCol.setCellValueFactory(new PropertyValueFactory<>("maxHoursOfWork"));

        horseTable.getColumns().addAll(nameCol, ageCol, conditionCol, maxHoursCol);

        VBox vBox = new VBox(10);
        vBox.getChildren().add(horseTable);

        // Buttons row
        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(15));
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label();

        Button add = new Button("Add Horse");
        add.setOnAction(e -> {
            horseController.showAddHorsePopup();
            refreshList();
        });

        Button update = new Button("Update Horse");
        update.setOnAction(e -> {
            Horse selected = horseTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                horseController.showUpdateHorsePopup(selected);
                refreshList();
            } else message.setText("Select a horse first");
        });

        Button delete = new Button("Delete Horse");
        delete.setOnAction(e -> {
            Horse selected = horseTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                boolean deleted = horseController.deleteHorse(selected);
                if (deleted) horseTable.getItems().remove(selected);
                message.setText(deleted ? "Deleted" : "Failed");
            } else message.setText("Select a horse first");
        });

        buttons.getChildren().addAll(add, update, delete);
        vBox.getChildren().addAll(buttons);

        // Show lessons button
        Button showLessons = new Button("Show Lessons");
        showLessons.setOnAction(e -> {
            Horse selected = horseTable.getSelectionModel().getSelectedItem();
            if (selected != null) horseController.showHorseLessonsPopup(selected);
            else message.setText("Select a horse first");
        });

        HBox secondRow = new HBox(15, showLessons);
        secondRow.setPadding(new Insets(5));
        secondRow.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(secondRow);

        HBox msgBox = new HBox(message);
        msgBox.setPadding(new Insets(5));
        msgBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(msgBox);

        return vBox;
    }
}