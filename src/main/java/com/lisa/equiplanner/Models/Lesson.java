package com.lisa.equiplanner.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Lesson {
    private Double duration;
    private String location;
    private Instructor instructor;
    private ObservableList<Combination> combinations;

    public Lesson(Double duration, String location, Instructor instructor) {
        this.duration = duration;
        this.location = location;
        this.instructor = instructor;
        this.combinations = FXCollections.observableArrayList();
    }

    public void addCombination(Combination combination) {
        combinations.add(combination);
    }

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
}
