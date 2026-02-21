package com.lisa.equiplanner.Models;

public class Horse {
    private int horseId;
    private String name;
    private Integer age;
    private Boolean isLame;
    private Integer maxHoursOfWork;

    public Horse(int horseId, String name, int age, Boolean isLame, int maxHoursOfWork) {
        this.horseId = horseId;
        this.name = name;
        this.age = age;
        this.isLame = isLame;
        this.maxHoursOfWork = maxHoursOfWork;
    }

    // --- Getters and setters ---

    public int getHorseId() {
        return horseId;
    }

    public void setHorseId(int horseId) {
        this.horseId = horseId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Boolean isLame() {
        return isLame;
    }

    public void setLame(Boolean lame) {
        this.isLame = lame;
    }

    public int getMaxHoursOfWork() {
        return this.maxHoursOfWork;
    }

    public void setMaxHoursOfWork(int maxHoursOfWork) {
        this.maxHoursOfWork = maxHoursOfWork;
    }

    @Override
    public String toString() {
        return name;
    }
}
