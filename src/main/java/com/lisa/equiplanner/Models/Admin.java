package com.lisa.equiplanner.Models;

public class Admin {
    private int adminID;
    private String username;
    private String passwordHash;

    // Constructor without ID (for new admins)
    public Admin(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Constructor with ID (if loading from DB)
    public Admin(int adminID, String username, String passwordHash) {
        this.adminID = adminID;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public int getAdminID() { return adminID; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }

    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
