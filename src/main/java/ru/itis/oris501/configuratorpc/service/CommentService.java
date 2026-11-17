package ru.itis.oris501.configuratorpc.service;

import ru.itis.oris501.configuratorpc.dao.CommentDao;
import ru.itis.oris501.configuratorpc.dao.impl.CommentDaoJdbc;
import ru.itis.oris501.configuratorpc.model.Comment;

import java.util.List;
import java.util.Optional;

public class CommentService {
    private final CommentDao commentDao;

    public CommentService() {
        this(new CommentDaoJdbc());
    }

    public CommentService(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public List<Comment> getByBuildId(int buildId) {
        idCheck(buildId);
        return commentDao.findByBuildId(buildId);
    }

    public Comment addComment(int buildId, int userId, String text) {
        idCheck(userId);
        idCheck(buildId);

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Комментарий не может быть пустым");
        }

        return commentDao.save(new Comment(buildId, userId, text));
    }

    private static void idCheck(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть > 0");
        }
    }
}
