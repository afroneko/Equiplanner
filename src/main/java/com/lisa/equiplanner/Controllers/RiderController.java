package com.lisa.equiplanner.Controllers;

import com.lisa.equiplanner.Database;
import com.lisa.equiplanner.Models.Address;
import com.lisa.equiplanner.Models.Horse;
import com.lisa.equiplanner.Models.Rider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class RiderController {

    private Database db;

    public RiderController(Database db) {
        this.db = db;
    }

    // --- Alle ruiters ophalen ---
    public ObservableList<Rider> getAllRiders() {
        ObservableList<Rider> list = FXCollections.observableArrayList();
        String query = """
                SELECT p.PersonID, p.FirstName, p.LastName, p.DateOfBirth,
                       p.Email, p.PhoneNumber,
                       a.AddressID, a.ZipCode, a.HouseNumber, a.Suffix,
                       a.Street, a.City, a.Country,
                       r.HasLeaseHorse
                FROM Person p
                JOIN Rider r ON p.PersonID = r.PersonID
                JOIN Address a ON p.AddressID = a.AddressID
                """;
        try (Connection conn = db.getConn();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Address address = new Address(
                        rs.getInt("AddressID"),
                        rs.getString("ZipCode"),
                        rs.getInt("HouseNumber"),
                        rs.getString("Suffix"),
                        rs.getString("Street"),
                        rs.getString("City"),
                        rs.getString("Country")
                );
                Rider rider = new Rider(
                        rs.getInt("PersonID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("DateOfBirth"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        address,
                        rs.getBoolean("HasLeaseHorse")
                );
                list.add(rider);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- Ruiter verwijderen ---
    public boolean deleteRider(Rider rider) {
        try {
            Connection conn = db.getConn();
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM Rider WHERE PersonID = ?");
            ps1.setInt(1, rider.getPersonId());
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Person WHERE PersonID = ?");
            ps2.setInt(1, rider.getPersonId());
            ps2.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Popup om ruiter toe te voegen ---
    public void showAddRiderPopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Ruiter toevoegen");

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField dob = new TextField();
        dob.setPromptText("Date of Birth (yyyy-mm-dd)");
        TextField email = new TextField();
        email.setPromptText("Email");
        TextField phone = new TextField();
        phone.setPromptText("Phone");
        TextField zip = new TextField();
        zip.setPromptText("Zip Code");
        TextField houseNo = new TextField();
        houseNo.setPromptText("House Number");
        TextField street = new TextField();
        street.setPromptText("Street");
        TextField city = new TextField();
        city.setPromptText("City");
        TextField country = new TextField();
        country.setPromptText("Country");
        CheckBox lease = new CheckBox("Has Lease Horse");

        Label message = new Label();

        Button addButton = new Button("Opslaan");
        addButton.setOnAction(e -> {
            try {
                // --- Adress toevoegen ---
                Connection conn = db.getConn();
                System.out.println(conn.isClosed());
                PreparedStatement psAddress = conn.prepareStatement(
                        "INSERT INTO Address (ZipCode, HouseNumber, Street, City, Country) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                psAddress.setString(1, zip.getText());
                psAddress.setInt(2, Integer.parseInt(houseNo.getText()));
                psAddress.setString(3, street.getText());
                psAddress.setString(4, city.getText());
                psAddress.setString(5, country.getText());
                psAddress.executeUpdate();

                ResultSet keys = psAddress.getGeneratedKeys();
                keys.next();
                int addressId = keys.getInt(1);

                // --- Persoon toevoegen ---
                PreparedStatement psPerson = conn.prepareStatement(
                        "INSERT INTO Person (FirstName, LastName, DateOfBirth, Email, PhoneNumber, AddressID) VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );

                psPerson.setString(1, firstName.getText());
                psPerson.setString(2, lastName.getText());
                psPerson.setString(3, dob.getText());
                psPerson.setString(4, email.getText());
                psPerson.setString(5, phone.getText());
                psPerson.setInt(6, addressId);
                psPerson.executeUpdate();

                ResultSet personKeys = psPerson.getGeneratedKeys();
                personKeys.next();
                int personId = personKeys.getInt(1);

                // --- Ruiter toevoegen ---
                PreparedStatement psRider = conn.prepareStatement(
                        "INSERT INTO Rider (PersonID, HasLeaseHorse) VALUES (?, ?)"
                );

                psRider.setInt(1, personId);
                psRider.setBoolean(2, lease.isSelected());
                psRider.executeUpdate();

                message.setText("Ruiter succesvol toegevoegd!");

            } catch (Exception ex) {
                ex.printStackTrace();
                message.setText("Error adding rider");
            }
        });

        VBox layout = new VBox(10,
                firstName, lastName, dob, email, phone,
                zip, houseNo, street, city, country,
                lease, addButton, message);

        layout.setPadding(new javafx.geometry.Insets(10));
        popup.setScene(new Scene(layout, 350, 500));
        popup.showAndWait();
    }

    // --- Popup om ruiter aan te passen ---
    public void showUpdateRiderPopup(Rider rider) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Ruiter aanpassen");

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField dob = new TextField();
        dob.setPromptText("Date of Birth (yyyy-mm-dd)");
        TextField email = new TextField();
        email.setPromptText("Email");
        TextField phone = new TextField();
        phone.setPromptText("Phone");
        TextField zip = new TextField();
        zip.setPromptText("Zip Code");
        TextField houseNo = new TextField();
        houseNo.setPromptText("House Number");
        TextField street = new TextField();
        street.setPromptText("Street");
        TextField city = new TextField();
        city.setPromptText("City");
        TextField country = new TextField();
        country.setPromptText("Country");
        CheckBox lease = new CheckBox("Has Lease Horse");

        Label message = new Label();

        Button updateButton = new Button("Opslaan");
        updateButton.setOnAction(e -> {
            try {
                // --- Adress aanpassen ---
                Connection conn = db.getConn();
                PreparedStatement psAddress = conn.prepareStatement(
                        "UPDATE Address SET ZipCode = ?, HouseNumber = ?, Street = ?, City = ?, Country =? WHERE AddressID = ?"
                );
                psAddress.setString(1, zip.getText());
                psAddress.setInt(2, Integer.parseInt(houseNo.getText()));
                psAddress.setString(3, street.getText());
                psAddress.setString(4, city.getText());
                psAddress.setString(5, country.getText());
                psAddress.executeUpdate();

                // --- Persoon aanpassen ---
                PreparedStatement psPerson = conn.prepareStatement(
                        "UPDATE Person SET FirstName = ?, LastName = ?, DateOfBirth = ?, Email = ?, PhoneNumber = ?, AddressID = ? WHERE PersonID = ?"
                );

                psPerson.setString(1, firstName.getText());
                psPerson.setString(2, lastName.getText());
                psPerson.setString(3, dob.getText());
                psPerson.setString(4, email.getText());
                psPerson.setString(5, phone.getText());
                psPerson.setInt(6, rider.getPersonId());
                psPerson.executeUpdate();

                // --- Ruiter aanpassen ---
                PreparedStatement psRider = conn.prepareStatement(
                        "UPDATE Rider SET PersonID = ?, HasLeaseHorse = ? WHERE personId = ?"
                );

                psRider.setInt(1, rider.getPersonId());
                psRider.setBoolean(2, lease.isSelected());
                psRider.executeUpdate();

                message.setText("Ruiter succesvol aangepasd!");

            } catch (Exception ex) {
                message.setText("Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10,
                firstName, lastName, dob, email, phone,
                zip, houseNo, street, city, country,
                lease, updateButton, message);
        layout.setPadding(new javafx.geometry.Insets(10));
        popup.setScene(new Scene(layout, 300, 250));
        popup.showAndWait();
    }

    // --- Show which lessons a horse is in ---
    public void showRiderLessonsPopup(Rider rider) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Lessons for " + rider.toString());
        ListView<String> lessonsList = new ListView<>();

        try {
            Connection conn = db.getConn();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT l.LessonDate, p.FirstName, p.LastName " +
                            "FROM Combination c " +
                            "JOIN Lesson l ON c.LessonID = l.LessonID " +
                            "JOIN Person p ON c.riderID = p.PersonID " +
                            "WHERE c.horseID = ?"
            );
            ps.setInt(1, rider.getPersonId());
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