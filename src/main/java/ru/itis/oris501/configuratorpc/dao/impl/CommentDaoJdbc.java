package ru.itis.oris501.configuratorpc.dao.impl;

import ru.itis.oris501.configuratorpc.dao.CommentDao;
import ru.itis.oris501.configuratorpc.model.Comment;
import ru.itis.oris501.configuratorpc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoJdbc implements CommentDao {
    private static final String SELECT_BY_BUILD_ID = "SELECT c.*, u.username FROM comments c JOIN users u ON c.user_id = u.id WHERE c.build_id = ? ORDER BY c.id DESC";
    private static final String INSERT = "INSERT INTO comments (build_id, user_id, text) " +
            "VALUES (?, ?, ?) RETURNING id";

    @Override
    public List<Comment> findByBuildId(int buildId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_BUILD_ID)) {
            ps.setInt(1, buildId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comments.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("findByBuildId не удалось выполнить", e);
        }
        return comments;
    }

    @Override
    public Comment save(Comment comment) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setInt(1, comment.getBuildId());
            ps.setInt(2, comment.getUserId());
            ps.setString(3, comment.getText());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) comment.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить комментарий", e);
        }
        return comment;
    }

    private Comment mapRow(ResultSet rs) throws SQLException {
        Comment comment = new Comment(
                rs.getInt("id"),
                rs.getInt("build_id"),
                rs.getInt("user_id"),
                rs.getString("text")
        );
        comment.setUsername(rs.getString("username"));
        return comment;
    }
}
