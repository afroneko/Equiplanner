package com.lisa.equiplanner.Controllers;

import com.lisa.equiplanner.Database;
import com.lisa.equiplanner.Models.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminController {
    private Database db;
    private Statement stmt;

    public AdminController(Database db) {
        this.db = db;
        try {
            this.stmt = db.getConn().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Add new admin with plaintext password, hashes it before storing
    public void add(Admin admin, String plainPassword) {
        String hashed = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        String sql = "INSERT INTO Admin (Username, Password) VALUES (?, ?)";

        try (PreparedStatement pstmt = db.getConn().prepareStatement(sql)) {
            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, hashed);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verify login credentials (username + plaintext password)
    public boolean verifyLogin(String username, String plainPassword) {
        String sql = "SELECT Password FROM Admin WHERE Username = ?";

        try (PreparedStatement pstmt = db.getConn().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("Password");
                return BCrypt.checkpw(plainPassword, storedHash);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;  // user not found or password incorrect
    }
}
