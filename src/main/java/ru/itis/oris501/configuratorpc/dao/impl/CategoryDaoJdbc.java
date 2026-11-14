package ru.itis.oris501.configuratorpc.dao.impl;

import ru.itis.oris501.configuratorpc.dao.CategoryDao;
import ru.itis.oris501.configuratorpc.model.Category;
import ru.itis.oris501.configuratorpc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoJdbc implements CategoryDao {

    private static final String SELECT_ALL = "SELECT * FROM categories ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT * FROM categories WHERE id = ?";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) categories.add(mapRow(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    @Override
    public Optional<Category> findById(int id) {
        Optional<Category> category = Optional.empty();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)){
            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    category = Optional.of(mapRow(rs));
                }
                return category;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Category mapRow(ResultSet rs) throws SQLException {
        Category category = new Category(
                rs.getInt("id"),
                rs.getString("name")
        );
        return category;
    }


}
