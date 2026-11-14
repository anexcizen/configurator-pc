package ru.itis.oris501.configuratorpc.dao.impl;

import ru.itis.oris501.configuratorpc.dao.ComponentDao;
import ru.itis.oris501.configuratorpc.model.Component;
import ru.itis.oris501.configuratorpc.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentDaoJdbc implements ComponentDao {

    private static final String SELECT_ALL = "SELECT * FROM components ORDER BY id";
    private static final String SELECT_BY_CATEGORY = "SELECT * FROM components WHERE category_id = ? ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT * FROM components WHERE id = ?";
    private static final String INSERT = "INSERT INTO components (name, category_id, price, image_path, socket, ram_type, power_watts) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE = "UPDATE components SET name=?, category_id=?, price=?, image_path=?, socket=?, ram_type=?, power_watts=? WHERE id=?";
    private static final String DELETE = "DELETE FROM components WHERE id=?";

    @Override
    public List<Component> findAll() {
        List<Component> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("findAll не получилось выполнить", e);
        }
        return list;
    }

    @Override
    public List<Component> findByCategory(int categoryId) {
        List<Component> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_CATEGORY)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("findByCategory не получилось выполнить", e);
        }
        return list;
    }

    @Override
    public Optional<Component> findById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("findById не получилось выполнить", e);
        }
        return Optional.empty();
    }

    @Override
    public Component save(Component component) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            PasteIntoPreparedStatement(component, ps);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) component.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("сохранить компонент не получилось", e);
        }
        return component;
    }

    @Override
    public void update(Component component) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            PasteIntoPreparedStatement(component, ps);
            ps.setInt(8, component.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("обновить не получилось", e);
        }
    }

    private static void PasteIntoPreparedStatement(Component c, PreparedStatement ps) throws SQLException {
        ps.setString(1, c.getName());
        ps.setInt(2, c.getCategoryId());
        ps.setBigDecimal(3, c.getPrice());
        ps.setString(4, c.getImagePath());
        ps.setString(5, c.getSocket());
        ps.setString(6, c.getRamType());
        if (c.getPowerWatts() == null) ps.setNull(7, Types.INTEGER);
        else ps.setInt(7, c.getPowerWatts());
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("удалить не получилось", e);
        }
    }

    private Component mapRow(ResultSet rs) throws SQLException {
        int pw = rs.getInt("power_watts");
        Integer powerWatts = rs.wasNull() ? null : pw;
        return new Component(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("category_id"),
                rs.getBigDecimal("price"),
                rs.getString("image_path"),
                rs.getString("socket"),
                rs.getString("ram_type"),
                powerWatts
        );
    }
}