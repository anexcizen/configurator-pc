package ru.itis.oris501.configuratorpc.model;

import java.time.LocalDateTime;

public class Build {
    private int id;
    private int userId;
    private String title;
    private String description;
    private boolean pub;
    private LocalDateTime createdAt;
    private String username;

    public Build(int id, int userId, String title, String description, boolean pub) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.pub = pub;
    }

    public Build(int userId, String title, String description, boolean pub) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.pub = pub;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
