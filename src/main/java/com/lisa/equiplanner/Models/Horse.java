package com.lisa.equiplanner.Models;

public class Horse {
    private String name;
    private int age;
    private Boolean condition;
    private int maxHoursOfWork;

    public Horse(String name, int age, Boolean condition, int maxHoursOfWork) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.maxHoursOfWork = maxHoursOfWork;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Boolean getCondition() {
        return condition;
    }

    public int getMaxHoursOfWork() {
        return maxHoursOfWork;
    }
}
