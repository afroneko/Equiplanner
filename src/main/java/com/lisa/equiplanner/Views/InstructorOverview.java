package com.lisa.equiplanner.Views;

import com.lisa.equiplanner.Controllers.InstructorController;
import com.lisa.equiplanner.Models.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InstructorOverview {
    private InstructorController instructorController;
    private ObservableList<Instructor> instructors;
    private TableView<Instructor> instructorTable;

    public InstructorOverview(InstructorController instructorController) {
        this.instructorController = instructorController;
        this.instructors = FXCollections.observableArrayList(instructorController.getAllInstructors());
    }

    private void refreshList() {
        instructors.setAll(instructorController.getAllInstructors());
    }

    public VBox getContent() {

        instructorTable = new TableView<>();
        instructorTable.setItems(instructors);

        TableColumn<Instructor, String> firstNameCol = new TableColumn<>("Voornaam");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Instructor, String> lastNameCol = new TableColumn<>("Achternaam");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Instructor, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Instructor, String> phoneCol = new TableColumn<>("Mobiel");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        TableColumn<Instructor, Boolean> trailCol = new TableColumn<>("Buitenrit");
        trailCol.setCellValueFactory(new PropertyValueFactory<>("doesTrailRides"));

        instructorTable.getColumns().addAll(firstNameCol, lastNameCol, emailCol, phoneCol, trailCol);

        VBox vBox = new VBox(10);
        vBox.getChildren().add(instructorTable);

        // Buttons rij
        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(15));
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label();

        Button add = new Button("Instructeur toevoegen");
        add.setOnAction(e -> {
            instructorController.showAddInstructorPopup();
            refreshList();
        });

        Button delete = new Button("instructeur verwijderen");
        delete.setOnAction(e -> {
            Instructor selected = instructorTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                instructorController.deleteInstructor(selected);
                instructors.remove(selected);
            }
        });

        buttons.getChildren().addAll( add, delete);
        vBox.getChildren().addAll(buttons);

        Button showLessons = new Button("Laat lessen zien");
        showLessons.setOnAction(e -> {
            Instructor selected = instructorTable.getSelectionModel().getSelectedItem();
            if (selected != null) instructorController.showInstructorLessonsPopup(selected);
            else message.setText("Selecteer eerst een instructeur!");
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
