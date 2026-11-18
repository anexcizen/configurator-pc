package ru.itis.oris501.configuratorpc.service;

import ru.itis.oris501.configuratorpc.dao.CategoryDao;
import ru.itis.oris501.configuratorpc.dao.ComponentDao;
import ru.itis.oris501.configuratorpc.model.Category;
import ru.itis.oris501.configuratorpc.model.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ComponentService {
    private final ComponentDao componentDao;
    private final CategoryDao categoryDao;

    public ComponentService(ComponentDao componentDao, CategoryDao categoryDao) {
        this.componentDao = componentDao;
        this.categoryDao = categoryDao;
    }

    public List<Component> findAll() {
        return componentDao.findAll();
    }

    public List<Component> findByCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("id категории должен быть > 0");
        }
        return componentDao.findByCategory(categoryId);
    }

    public Optional<Component> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть > 0");
        }
        return componentDao.findById(id);
    }

    public  Component create(Component c) {
        validate(c);
        return componentDao.save(c);
    }

    public void update(Component c) {
        if (c.getId() <= 0) {
            throw new IllegalArgumentException("id компонента некорректный");
        }
        if (componentDao.findById(c.getId()).isEmpty()) {
            throw new IllegalArgumentException("компонента с таким id не существует");
        }
        validate(c);

        componentDao.update(c);
    }

    public void delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id должен быть > 0");
        }

        if (componentDao.findById(id).isEmpty()) {
            throw new IllegalArgumentException("компонента с таким id не существует");
        }

        try {
            componentDao.delete(id);
        } catch (RuntimeException e) {
            if (e.getCause() instanceof java.sql.SQLException
                    && e.getCause().getMessage().contains("foreign key")) {
                throw new IllegalArgumentException("Компонент используется в сборках, удалить нельзя. Сначала уберите его из сборок.");
            }
            throw e;
        }
    }

    private void validate(Component c) {
        if (c == null) {
            throw new IllegalArgumentException("Компонент не может быть null");
        }
        if (c.getName() == null || c.getName().isBlank()) {
            throw new IllegalArgumentException("Название компонента не может быть пустым");
        }
        if (c.getPrice() == null || c.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Цена должна быть строго больше 0");
        }

        Category category = categoryDao.findById(c.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категории с таким id не существует"));
        String catName = category.getName();

        if ("Процессор".equals(catName) || "Материнская плата".equals(catName)) {
            if (c.getSocket() == null || c.getSocket().isBlank()) {
                throw new IllegalArgumentException("Для этой категории нужно указать сокет");
            }
        }
        if ("Материнская плата".equals(catName) || "Оперативная память".equals(catName)) {
            if (c.getRamType() == null || c.getRamType().isBlank()) {
                throw new IllegalArgumentException("Для этой категории нужно указать тип памяти (RAM)");
            }
        }
        if ("Процессор".equals(catName) || "Видеокарта".equals(catName) || "Блок питания".equals(catName)) {
            if (c.getPowerWatts() == null || c.getPowerWatts() <= 0) {
                throw new IllegalArgumentException("Для этой категории нужно указать мощность (Вт > 0)");
            }
        }
    }
}
