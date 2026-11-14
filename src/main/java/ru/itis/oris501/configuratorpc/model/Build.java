package ru.itis.oris501.configuratorpc.model;

import java.time.LocalDateTime;

public class Build {
    private final int id;
    private final int user_id;
    private final String title;
    private final String description;
    private final boolean is_public;
    private final LocalDateTime created_at;

    public Build(int id, int user_id, String title, String description, boolean is_public, LocalDateTime created_at) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.is_public = is_public;
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
