package ru.itis.oris501.configuratorpc.dao.impl;

import ru.itis.oris501.configuratorpc.dao.BuildDao;
import ru.itis.oris501.configuratorpc.model.Build;
import ru.itis.oris501.configuratorpc.model.Component;
import ru.itis.oris501.configuratorpc.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BuildDaoJdbc implements BuildDao {
    private static final String INSERT = "INSERT INTO builds (user_id, title, description, is_public) " +
            "VALUES (?, ?, ?, ?) RETURNING id";
    private static final String BUILDS_WHERE_ID = "SELECT * FROM builds WHERE id = ?";
    private static final String BUILDS_WHERE_USER_ID = "SELECT * FROM builds WHERE user_id = ?";
    private static final String INSERT_COMPONENT = "INSERT INTO build_components (build_id, component_id) VALUES (?, ?)";
    private static final String COMPONENT_WHERE_BC_BUILD_ID = "SELECT c.* FROM components c JOIN build_components bc ON c.id = bc.component_id WHERE bc.build_id = ?";
    private static final String DELETE = "DELETE FROM builds WHERE id = ?";
    private static final String DELETE_COMPONENT = "DELETE FROM build_components WHERE build_id = ? AND component_id = ?";
    private static final String UPDATE = "UPDATE builds SET title=?, description=?, is_public=? WHERE id=?";
    private static final String ALL_PUBLIC_BUILDS = "SELECT * FROM builds WHERE is_public = true ORDER BY id DESC";

    @Override
    public Build save(Build build) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setInt(1, build.getUserId());
            ps.setString(2, build.getTitle());
            ps.setString(3, build.getDescription());
            ps.setBoolean(4, build.isPub());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) build.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить сборку", e);
        }
        return build;
    }

    @Override
    public Optional<Build> findById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUILDS_WHERE_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToBuild(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти сборку по id=" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Build> findByUserId(int userId) {
        Build build;
        List<Build> builds = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUILDS_WHERE_USER_ID)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    build = mapToBuild(rs);
                    builds.add(build);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти сборки пользователя с id=" + userId, e);
        }
        return builds;
    }

    @Override
    public void addComponent(int buildId, int componentId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_COMPONENT)) {
            ps.setInt(1, buildId);
            ps.setInt(2, componentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось добавить компонент " + componentId + " в сборку с id=" + buildId, e);
        }
    }

    @Override
    public List<Component> findComponentsByBuildId(int buildId) {
        List<Component> components = new ArrayList<>();
        Component component;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COMPONENT_WHERE_BC_BUILD_ID)) {
            ps.setInt(1, buildId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    component = mapToComponent(rs);
                    components.add(component);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось найти компоненты сборки с id=" + buildId, e);
        }
        return components;
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить сборку с id=" + id, e);
        }
    }

    @Override
    public void removeComponent(int buildId, int componentId) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_COMPONENT)) {
            ps.setInt(1, buildId);
            ps.setInt(2, componentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить компонент из сборки", e);
        }
    }

    @Override
    public void update(Build build) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, build.getTitle());
            ps.setString(2, build.getDescription());
            ps.setBoolean(3, build.isPub());
            ps.setInt(4, build.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось обновить сборку", e);
        }
    }

    @Override
    public List<Build> findPublicBuilds() {
        List<Build> builds = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ALL_PUBLIC_BUILDS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                builds.add(mapToBuild(rs));
            }
        } catch(SQLException e){
            throw new RuntimeException("Не удалось найти публичные сборки", e);
        }
        return builds;
    }

        private static Build mapToBuild (ResultSet rs) throws SQLException {
            return new Build(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("is_public"));
        }

        private Component mapToComponent (ResultSet rs) throws SQLException {
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
