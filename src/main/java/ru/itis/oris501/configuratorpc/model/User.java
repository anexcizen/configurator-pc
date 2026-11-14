package ru.itis.oris501.configuratorpc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private final int id;
    private final String username;
    private final String password_hash;
    private final String role;
    private final LocalDateTime created_at;

    public User(int id, String username, String password_hash, String role, LocalDateTime created_at) {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.role = role;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
