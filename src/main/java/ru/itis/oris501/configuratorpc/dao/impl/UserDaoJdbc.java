package ru.itis.oris501.configuratorpc.dao.impl;

import ru.itis.oris501.configuratorpc.dao.UserDao;
import ru.itis.oris501.configuratorpc.model.User;
import ru.itis.oris501.configuratorpc.util.DBConnection;

import java.sql.*;
import java.util.Optional;

public class UserDaoJdbc implements UserDao {

    private static final String SELECT_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String INSERT = "INSERT INTO users (username, password_hash, salt, role) VALUES (?, ?, ?, ?) RETURNING id";

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<User> user = Optional.empty();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_USERNAME)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    user = Optional.of(mapRow(rs));
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти пользователя с ником: " + username, e);
        }
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> user = Optional.empty();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = Optional.of(mapRow(rs));
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("не удалось найти пользователя с id = " + id, e);
        }
    }

    @Override
    public User save(User user) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getSalt());
            ps.setString(4, user.getRole());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) user.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("сохранить пользователя не получилось", e);
        }
        return user;
    }

    private User mapRow(ResultSet rs) throws SQLException {
         User user = new User(
                 rs.getInt("id"), rs.getString("username"),
                 rs.getString("password_hash"), rs.getString("role"),
                 rs.getString("salt"), rs.getTimestamp("created_at").toLocalDateTime()
        );
        return user;
    }
}
