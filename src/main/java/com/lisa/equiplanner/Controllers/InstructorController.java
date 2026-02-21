package com.lisa.equiplanner.Controllers;

import com.lisa.equiplanner.Database;
import com.lisa.equiplanner.Models.Address;
import com.lisa.equiplanner.Models.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class InstructorController {
    private Database db;

    public InstructorController(Database db) {
        this.db = db;
    }

    // --- Alle instructeurs ophalen ---
    public ObservableList<Instructor> getAllInstructors() {
        ObservableList<Instructor> list = FXCollections.observableArrayList();
        String query = """
                SELECT p.PersonID, p.FirstName, p.LastName, p.DateOfBirth,
                       p.Email, p.PhoneNumber,
                       a.AddressID, a.ZipCode, a.HouseNumber, a.Suffix,
                       a.Street, a.City, a.Country,
                       i.DoesTrailRides
                FROM Person p
                JOIN Instructor i ON p.PersonID = i.PersonID
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
                Instructor instructor = new Instructor(
                        rs.getInt("PersonID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("DateOfBirth"),
                        rs.getString("Email"),
                        rs.getString("PhoneNumber"),
                        address,
                        rs.getBoolean("DoesTrailRides")
                );
                list.add(instructor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- Popup om instructeur toe te voegen ---
    public void showAddInstructorPopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Instructor toevoegen");

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
        CheckBox lease = new CheckBox("Gaat op buitenrit");

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

                // --- Instructeur toevoegen ---
                PreparedStatement psInstructor = conn.prepareStatement(
                        "INSERT INTO Instructor (PersonID, DoesTrailRides) VALUES (?, ?)"
                );

                psInstructor.setInt(1, personId);
                psInstructor.setBoolean(2, lease.isSelected());
                psInstructor.executeUpdate();

                message.setText("Instructeur succesvol toegevoegd!");

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

    // --- Instructeur verwijderen ---
    public boolean deleteInstructor(Instructor instructor) {
        try {
            Connection conn = db.getConn();
            PreparedStatement ps1 = conn.prepareStatement("DELETE FROM Instructor WHERE PersonID = ?");
            ps1.setInt(1, instructor.getPersonId());
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Person WHERE PersonID = ?");
            ps2.setInt(1, instructor.getPersonId());
            ps2.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Methode die laat zien in welke les de instructeur zit ---
    public void showInstructorLessonsPopup(Instructor instructor) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Lessons for " + instructor.getFirstName() + " " + instructor.getLastName());
        ListView<String> lessonsList = new ListView<>();

        try (Connection conn = db.getConn()) {

            // Get all lessons for this instructor
            String lessonQuery = """
            SELECT l.LessonID, l.LessonDate, l.StartTime, l.Location
            FROM Lesson l
            WHERE l.InstructorID = ?
            ORDER BY l.LessonDate, l.StartTime
        """;
            PreparedStatement psLesson = conn.prepareStatement(lessonQuery);
            psLesson.setInt(1, instructor.getPersonId());
            ResultSet rsLesson = psLesson.executeQuery();

            while (rsLesson.next()) {
                int lessonId = rsLesson.getInt("LessonID");
                Date lessonDate = rsLesson.getDate("LessonDate");
                Time startTime = rsLesson.getTime("StartTime");
                String location = rsLesson.getString("Location");

                // Get riders & horses for this lesson
                String comboQuery = """
                SELECT r.FirstName, r.LastName, h.Name AS HorseName
                FROM Combination c
                JOIN Person r ON c.RiderID = r.PersonID
                JOIN Horse h ON c.HorseID = h.HorseID
                WHERE c.LessonID = ?
            """;
                PreparedStatement psCombo = conn.prepareStatement(comboQuery);
                psCombo.setInt(1, lessonId);
                ResultSet rsCombo = psCombo.executeQuery();

                StringBuilder comboInfo = new StringBuilder();
                while (rsCombo.next()) {
                    comboInfo.append(rsCombo.getString("FirstName"))
                            .append(" ")
                            .append(rsCombo.getString("LastName"))
                            .append(" → ")
                            .append(rsCombo.getString("HorseName"))
                            .append("\n");
                }

                lessonsList.getItems().add(
                        lessonDate + " " + startTime + " at " + location + "\n" +
                                comboInfo.toString()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        VBox layout = new VBox(10, lessonsList);
        layout.setPadding(new javafx.geometry.Insets(10));
        popup.setScene(new Scene(layout, 500, 400));
        popup.showAndWait();
    }
}
