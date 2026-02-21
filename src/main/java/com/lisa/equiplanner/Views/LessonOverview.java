package com.lisa.equiplanner.Views;

import com.lisa.equiplanner.Controllers.LessonController;
import com.lisa.equiplanner.Models.Lesson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LessonOverview {

    private LessonController lessonController;
    private ObservableList<Lesson> lessons;
    private TableView<Lesson> lessonTable;

    public LessonOverview(LessonController lessonController) {
        this.lessonController = lessonController;
        this.lessons = FXCollections.observableArrayList(lessonController.getAllLessons());
    }

    private void refreshList() {
        lessons.setAll(lessonController.getAllLessons());
    }

    public VBox getContent() {

        lessonTable = new TableView<>();
        lessonTable.setItems(lessons);
        TableColumn<Lesson, Integer> weekCol = new TableColumn<>("Week");
        weekCol.setCellValueFactory(new PropertyValueFactory<>("weekNumber"));
        TableColumn<Lesson, String> dayCol = new TableColumn<>("Dag");
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        TableColumn<Lesson, String> dateCol = new TableColumn<>("Datum");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        TableColumn<Lesson, String> timeCol = new TableColumn<>("Starttijd");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        TableColumn<Lesson, Double> durationCol = new TableColumn<>("Duur");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        TableColumn<Lesson, String> locationCol = new TableColumn<>("Locatie");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        TableColumn<Lesson, String> instructorCol = new TableColumn<>("Instructeur");
        instructorCol.setCellValueFactory(new PropertyValueFactory<>("instructorName"));

        lessonTable.getColumns().addAll(
                weekCol, dayCol, dateCol,
                timeCol, durationCol,
                locationCol, instructorCol
        );

        VBox vBox = new VBox(10);
        vBox.getChildren().add(lessonTable);

        // Buttons row
        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(15));
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label();

        Button add = new Button("Add Lesson");
        add.setOnAction(e -> {
            lessonController.showAddLessonPopup();
            refreshList();
        });

//        Button update = new Button("Les aanpassen");
//        update.setOnAction(e -> {
//            Lesson selected = lessonTable.getSelectionModel().getSelectedItem();
//            if (selected != null) {
//                lessonController.showUpdateLessonPopup(selected);
//                refreshList();
//            } else message.setText("Selecteer eerst een les");
//        });
//
        Button delete = new Button("Les verwijderen");
        delete.setOnAction(e -> {
            Lesson selected = lessonTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                lessonController.deleteLesson(selected);
                lessons.remove(selected);
            }
        });

        buttons.getChildren().addAll(add, delete);
        vBox.getChildren().add(buttons);

        // Second row
        Button showCombinations = new Button("Show Combinations");
        showCombinations.setOnAction(e -> {
            Lesson selected = lessonTable.getSelectionModel().getSelectedItem();
            if (selected != null)
                lessonController.showLessonCombinationsPopup(selected);
            else message.setText("Selecteer eerst een les");
        });

        HBox secondRow = new HBox(15, showCombinations);
        secondRow.setPadding(new Insets(5));
        secondRow.setAlignment(Pos.CENTER);

        vBox.getChildren().add(secondRow);

        HBox msgBox = new HBox(message);
        msgBox.setPadding(new Insets(5));
        msgBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(msgBox);

        return vBox;
    }
}