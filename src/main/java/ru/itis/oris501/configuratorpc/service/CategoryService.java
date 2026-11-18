package ru.itis.oris501.configuratorpc.service;

import ru.itis.oris501.configuratorpc.dao.CategoryDao;
import ru.itis.oris501.configuratorpc.model.Category;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Optional<Category> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть > 0");
        }
        return categoryDao.findById(id);
    }
}
