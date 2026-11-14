package ru.itis.oris501.configuratorpc.dao;

import ru.itis.oris501.configuratorpc.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> findAll();
    Optional<Category> findById(int id);
}

