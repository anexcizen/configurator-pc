package ru.itis.oris501.configuratorpc.dao;

import ru.itis.oris501.configuratorpc.model.Component;

import java.util.List;
import java.util.Optional;

public interface ComponentDao {
    List<Component> findAll();
    List<Component> findByCategory(int categoryId);
    Optional<Component> findById(int id);
    Component save(Component component);
    void update(Component component);
    void delete(int id);
}
