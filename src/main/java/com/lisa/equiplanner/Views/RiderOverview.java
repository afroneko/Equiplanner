package com.lisa.equiplanner.Views;

import com.lisa.equiplanner.Controllers.RiderController;
import com.lisa.equiplanner.Models.Horse;
import com.lisa.equiplanner.Models.Rider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RiderOverview {

    private RiderController riderController;
    private ObservableList<Rider> riders;
    private TableView<Rider> riderTable;

    public RiderOverview(RiderController riderController) {
        this.riderController = riderController;
        this.riders = FXCollections.observableArrayList(riderController.getAllRiders());
    }

    private void refreshList() {
        riders.setAll(riderController.getAllRiders());
    }

    public VBox getContent() {
        riderTable = new TableView<>();
        riderTable.setItems(riders);

        TableColumn<Rider, String> firstNameCol = new TableColumn<>("Voornaam");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Rider, String> lastNameCol = new TableColumn<>("Achternaam");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Rider, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Rider, String> phoneCol = new TableColumn<>("Mobiel");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
        TableColumn<Rider, Boolean> leaseCol = new TableColumn<>("Verzorgpaard");
        leaseCol.setCellValueFactory(new PropertyValueFactory<>("hasLeaseHorse"));

        riderTable.getColumns().addAll(firstNameCol, lastNameCol, emailCol, phoneCol, leaseCol);

        VBox vBox = new VBox(10);
        vBox.getChildren().add(riderTable);

        // Buttons row
        HBox buttons = new HBox(15);
        buttons.setPadding(new Insets(15));
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label();

        Button add = new Button("Add Rider");
        add.setOnAction(e -> {
            riderController.showAddRiderPopup();
            refreshList();
        });

        Button update = new Button("Ruiter aanpassen");
        update.setOnAction(e -> {
            Rider selected = riderTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                riderController.showUpdateRiderPopup(selected);
                refreshList();
            } else message.setText("Selecteer eerst een ruiter");
        });

        Button delete = new Button("Ruiter verwijderen");
        delete.setOnAction(e -> {
            Rider selected = riderTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                riderController.deleteRider(selected);
                riders.remove(selected);
            }
        });

        buttons.getChildren().addAll(add, update, delete);
        vBox.getChildren().addAll(buttons);

        Button showLessons = new Button("Show Lessons");
        showLessons.setOnAction(e -> {
            Rider selected = riderTable.getSelectionModel().getSelectedItem();
            if (selected != null) riderController.showRiderLessonsPopup(selected);
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