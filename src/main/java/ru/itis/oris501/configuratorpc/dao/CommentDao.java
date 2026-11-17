package ru.itis.oris501.configuratorpc.dao;

import ru.itis.oris501.configuratorpc.model.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findByBuildId(int buildId);
    Comment save(Comment comment);
}
