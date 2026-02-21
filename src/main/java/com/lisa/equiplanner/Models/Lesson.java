package com.lisa.equiplanner.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;

public class Lesson {
    private Integer lessonId;
    private LocalDate lessonDate;
    private LocalTime startTime;
    private Double duration;
    private String location;
    private Instructor instructor;
    private ObservableList<Combination> combinations;

    public Lesson(Integer lessonId, LocalDate lessonDate,
                  LocalTime startTime,Double duration,
                  String location, Instructor instructor) {
        this.lessonId = lessonId;
        this.lessonDate = lessonDate;
        this.startTime = startTime;
        this.duration = duration;
        this.location = location;
        this.instructor = instructor;
        this.combinations = FXCollections.observableArrayList();
    }

    // --- Eindtijd les berekenen ---
    public LocalTime getEndTime() {
        return startTime.plusMinutes((long)(duration * 60));
    }

    // --- Getters en setters ---

    public ObservableList<Combination> getCombinations() {
        return combinations;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Double getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public Integer getLessonId() {return lessonId;}

    public LocalDate getLessonDate() {return lessonDate;}

    public LocalTime getStartTime() {return startTime;}

    public void addCombination(Combination combination) {
        combinations.add(combination);
    }
}
