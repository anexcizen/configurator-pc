package ru.itis.oris501.configuratorpc.model;

import java.time.LocalDateTime;

public class Build {
    private int id;
    private int user_id;
    private String title;
    private String description;
    private boolean is_public;
    private LocalDateTime created_at;

    public Build(int id, int user_id, String title, String description, boolean is_public, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.is_public = is_public;
        this.created_at = created_at;
    }

    public Build(int user_id, String title, String description, boolean is_public, LocalDateTime created_at) {
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.is_public = is_public;
        this.created_at = created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean is_public() {
        return is_public;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
