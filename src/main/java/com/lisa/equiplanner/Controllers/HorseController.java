package com.lisa.equiplanner.Controllers;

import com.lisa.equiplanner.Database;
import com.lisa.equiplanner.Models.Horse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.*;

public class HorseController {

    private Database db;

    public HorseController(Database db) {
        this.db = db;
    }

    // --- Fetch all horses ---
    public ObservableList<Horse> getAllHorses() {
        ObservableList<Horse> list = FXCollections.observableArrayList();
        try {
            Connection conn = db.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Horse");

            while (rs.next()) {
                Horse h = new Horse(
                        rs.getInt("HorseId"),
                        rs.getString("Name"),
                        rs.getInt("Age"),
                        rs.getBoolean("IsLame"),
                        rs.getInt("MaxHoursOfWork")
                );
                list.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- Delete horse ---
    public boolean deleteHorse(Horse horse) {
        try {
            Connection conn = db.getConn();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Horse WHERE HorseId = ?");
            ps.setInt(1, horse.getHorseId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Popup for adding a horse ---
    public void showAddHorsePopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Paard toevoegen");

        TextField nameField = new TextField();
        nameField.setPromptText("Naam");

        TextField ageField = new TextField();
        ageField.setPromptText("Leeftijd");

        CheckBox conditionBox = new CheckBox("Kreupel");

        TextField maxHoursField = new TextField();
        maxHoursField.setPromptText("Inzetbaarheid");

        Label message = new Label();

        Button addButton = new Button("Opslaan");
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                boolean isLame = conditionBox.isSelected();
                int maxHours = Integer.parseInt(maxHoursField.getText());

                Connection conn = db.getConn();
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Horse (Name, Age, isLame, MaxHoursOfWork) VALUES (?, ?, ?, ?)"
                );
                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setBoolean(3, isLame);
                ps.setInt(4, maxHours);
                ps.executeUpdate();

                message.setText("Horse added successfully!");

            } catch (Exception ex) {
                message.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10, nameField, ageField, conditionBox, maxHoursField, addButton, message);
        layout.setPadding(new javafx.geometry.Insets(10));
        popup.setScene(new Scene(layout, 300, 250));
        popup.showAndWait();
    }

    // --- Popup for updating a horse ---
    public void showUpdateHorsePopup(Horse horse) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Update Horse");

        TextField nameField = new TextField(horse.getName());
        TextField ageField = new TextField(String.valueOf(horse.getAge()));
        CheckBox conditionBox = new CheckBox("Available");
        conditionBox.setSelected(horse.isLame());
        TextField maxHoursField = new TextField(String.valueOf(horse.getMaxHoursOfWork()));
        Label message = new Label();

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                boolean condition = conditionBox.isSelected();
                int maxHours = Integer.parseInt(maxHoursField.getText());

                Connection conn = db.getConn();
                PreparedStatement ps = conn.prepareStatement(
                        "UPDATE Horse SET Name = ?, Age = ?, isLame = ?, MaxHoursOfWork = ? WHERE HorseId = ?"
                );
                ps.setString(1, name);
                ps.setInt(2, age);
                ps.setBoolean(3, condition);
                ps.setInt(4, maxHours);
                ps.setInt(5, horse.getHorseId());
                ps.executeUpdate();

                message.setText("Horse updated successfully!");

            } catch (Exception ex) {
                message.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10, nameField, ageField, conditionBox, maxHoursField, updateButton, message);
        layout.setPadding(new javafx.geometry.Insets(10));
        popup.setScene(new Scene(layout, 300, 250));
        popup.showAndWait();
    }

    // --- Show which lessons a horse is in ---
    public void showHorseLessonsPopup(Horse horse) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Lessons for " + horse.getName());

        ListView<String> lessonsList = new ListView<>();

        try {
            Connection conn = db.getConn();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT l.LessonDate, p.FirstName, p.LastName " +
                            "FROM Combination c " +
                            "JOIN Lesson l ON c.LessonID = l.LessonID " +
                            "JOIN Person p ON c.rider_id = p.PersonID " +
                            "WHERE c.horse_id = ?"
            );
            ps.setInt(1, horse.getHorseId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String lessonInfo = rs.getDate("LessonDate") + " - Rider: " +
                        rs.getString("FirstName") + " " + rs.getString("LastName");
                lessonsList.getItems().add(lessonInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        VBox layout = new VBox(10, lessonsList);
        layout.setPadding(new javafx.geometry.Insets(10));
        popup.setScene(new Scene(layout, 400, 300));
        popup.showAndWait();
    }
}