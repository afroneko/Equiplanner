package com.lisa.equiplanner.Models;

public class Combination {
    private Rider rider;
    private Horse horse;

    public Combination(Rider rider, Horse horse) {
        this.rider = rider;
        this.horse = horse;
    }

    // Getters
    public Rider getRider() {
        return rider;
    }

    public Horse getHorse() {
        return horse;
    }
}
