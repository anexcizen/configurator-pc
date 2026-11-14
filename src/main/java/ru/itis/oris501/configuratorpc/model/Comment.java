package ru.itis.oris501.configuratorpc.model;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private int buildId;
    private int userId;
    private String text;
    private LocalDateTime createdAt;

    public Comment(int id, int buildId, int userId, String text, LocalDateTime createdAt) {
        this.id = id;
        this.buildId = buildId;
        this.userId = userId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public Comment(int buildId, int userId, String text, LocalDateTime createdAt) {
        this.buildId = buildId;
        this.userId = userId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getBuildId() {
        return buildId;
    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
