package com.lisa.equiplanner.Controllers;

import com.lisa.equiplanner.Database;
import com.lisa.equiplanner.Models.Horse;
import com.lisa.equiplanner.Models.Instructor;
import com.lisa.equiplanner.Models.Lesson;
import com.lisa.equiplanner.Models.Rider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class LessonController {
    private Database db;
    private InstructorController ic;
    private RiderController rc;
    private HorseController hc;

    public LessonController(Database db,  InstructorController ic, RiderController rc, HorseController hc) {
        this.db = db;
        this.ic = ic;
        this.rc = rc;
        this.hc = hc;
    }

    // --- Alle lessen ophalen ---
    public ObservableList<Lesson> getAllLessons() {
        ObservableList<Lesson> list = FXCollections.observableArrayList();
        String query = """
        SELECT l.LessonID, l.LessonDate, l.StartTime,
                       l.Duration, l.Location,
                       p.FirstName, p.LastName
                FROM Lesson l
                JOIN Instructor i ON l.InstructorID = i.PersonID
                JOIN Person p ON i.PersonID = p.PersonID
                """;

        try (Connection conn = db.getConn();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                LocalDate date = rs.getDate("LessonDate").toLocalDate();
                LocalTime time = rs.getTime("StartTime").toLocalTime();

                String instructorName =
                        rs.getString("FirstName") + " " +
                                rs.getString("LastName");

                Lesson lesson = new Lesson(
                        rs.getInt("LessonID"),
                        date,
                        time,
                        rs.getDouble("Duration"),
                        rs.getString("Location"),
                        instructorName
                );

                list.add(lesson);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // --- Les verwijderen ---
    public boolean deleteLesson(Lesson lesson) {

        String query = "DELETE FROM Lesson WHERE LessonID = ?";

        try (Connection conn = db.getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, lesson.getLessonId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Popup voor het toevoegen van een les
    public void showAddLessonPopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Add Lesson");

        // --- Lesson details ---
        DatePicker datePicker = new DatePicker();
        TextField timeField = new TextField("09:00");
        TextField durationField = new TextField();
        TextField locationField = new TextField("Location");
        ComboBox<Instructor> instructorBox = new ComboBox<>(ic.getAllInstructors());

        // --- Riders and their horse assignment ---
        ObservableList<Rider> riders = rc.getAllRiders();  // get all riders
        ObservableList<Horse> horses = hc.getAllHorses();  // get all horses

        TableView<RiderHorseAssignment> assignmentTable = new TableView<>();
        assignmentTable.setEditable(true);
        ObservableList<RiderHorseAssignment> assignments = FXCollections.observableArrayList();
        for (Rider r : riders) assignments.add(new RiderHorseAssignment(r));

        assignmentTable.setItems(assignments);

        TableColumn<RiderHorseAssignment, String> riderCol = new TableColumn<>("Rider");
        riderCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRider().getFirstName() + " " +
                data.getValue().getRider().getLastName()));

        TableColumn<RiderHorseAssignment, Horse> horseCol = new TableColumn<>("Horse");
        horseCol.setCellValueFactory(data -> data.getValue().horseProperty());
        horseCol.setCellFactory(ComboBoxTableCell.forTableColumn(horses));
        horseCol.setEditable(true);

        assignmentTable.getColumns().addAll(riderCol, horseCol);

        // --- Save button ---
        Button save = new Button("Save");
        save.setOnAction(e -> {
            try (Connection conn = db.getConn()) {

                // 1️⃣ Insert lesson
                String lessonQuery = "INSERT INTO Lesson (LessonDate, StartTime, Duration, Location, InstructorID) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psLesson = conn.prepareStatement(lessonQuery, Statement.RETURN_GENERATED_KEYS);

                psLesson.setDate(1, Date.valueOf(datePicker.getValue()));
                psLesson.setTime(2, Time.valueOf(timeField.getText() + ":00"));
                psLesson.setDouble(3, Double.parseDouble(durationField.getText()));
                psLesson.setString(4, locationField.getText());
                psLesson.setInt(5, instructorBox.getValue().getPersonId());

                psLesson.executeUpdate();
                ResultSet lessonKeys = psLesson.getGeneratedKeys();
                lessonKeys.next();
                int lessonId = lessonKeys.getInt(1);

                // 2️⃣ Insert combinations
                String comboQuery = "INSERT INTO Combination (LessonID, RiderID, HorseID) VALUES (?, ?, ?)";
                PreparedStatement psCombo = conn.prepareStatement(comboQuery);

                for (RiderHorseAssignment assignment : assignments) {
                    if (assignment.getHorse() != null) {
                        psCombo.setInt(1, lessonId);
                        psCombo.setInt(2, assignment.getRider().getPersonId());
                        psCombo.setInt(3, assignment.getHorse().getHorseId());
                        psCombo.executeUpdate();
                    }
                }

                popup.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox layout = new VBox(10,
                new Label("Lesson Date:"), datePicker,
                new Label("Start Time:"), timeField,
                new Label("Duration:"), durationField,
                new Label("Location:"), locationField,
                new Label("Instructor:"), instructorBox,
                new Label("Assign Riders to Horses:"), assignmentTable,
                save);

        layout.setPadding(new Insets(10));
        popup.setScene(new Scene(layout, 500, 600));
        popup.showAndWait();
    }

    // --- Helper class for the TableView ---
    public static class RiderHorseAssignment {
        private final Rider rider;
        private final ObjectProperty<Horse> horse = new SimpleObjectProperty<>();

        public RiderHorseAssignment(Rider rider) {
            this.rider = rider;
        }

        public Rider getRider() { return rider; }
        public Horse getHorse() { return horse.get(); }
        public void setHorse(Horse horse) { this.horse.set(horse); }
        public ObjectProperty<Horse> horseProperty() { return horse; }
    }

    // --- Toevoegen van ruiter en paard combinaties ---
    public void addCombination(int lessonId, int riderId, int horseId) {

        String query = """
        INSERT INTO Combination (LessonID, Rider_ID, Horse_ID)
        VALUES (?, ?, ?)
    """;

        try (Connection conn = db.getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, lessonId);
            ps.setInt(2, riderId);
            ps.setInt(3, horseId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Popup om combinaties weer te geven
    public void showLessonCombinationsPopup(Lesson lesson) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Combinations for lesson on " + lesson.getLessonDate());

        ListView<String> combinationsList = new ListView<>();

        String query = """
        SELECT h.Name,
               p.FirstName,
               p.LastName
        FROM Combination c
        JOIN Horse h ON c.HorseID = h.HorseId
        JOIN Rider r ON c.RiderID = r.PersonID
        JOIN Person p ON r.PersonID = p.PersonID
        WHERE c.LessonID = ?
    """;

        try (Connection conn = db.getConn();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, lesson.getLessonId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String horseName = rs.getString("Name");
                String riderName = rs.getString("FirstName") + " " +
                        rs.getString("LastName");

                combinationsList.getItems().add(
                        riderName + " - " + horseName
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        VBox layout = new VBox(10, combinationsList);
        layout.setPadding(new javafx.geometry.Insets(10));

        popup.setScene(new Scene(layout, 400, 300));
        popup.showAndWait();
    }
}
