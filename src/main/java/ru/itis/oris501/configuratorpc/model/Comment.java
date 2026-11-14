package ru.itis.oris501.configuratorpc.model;

import java.time.LocalDateTime;

public class Comment {
    private final int id;
    private final int build_id;
    private final int user_id;
    private final String text;
    private final LocalDateTime created_at;

    public Comment(int id, int build_id, int user_id, String text, LocalDateTime created_at) {
        this.id = id;
        this.build_id = build_id;
        this.user_id = user_id;
        this.text = text;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public int getBuild_id() {
        return build_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
