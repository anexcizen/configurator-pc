package ru.itis.oris501.configuratorpc.dao;

import ru.itis.oris501.configuratorpc.model.Build;
import ru.itis.oris501.configuratorpc.model.Component;

import java.util.List;
import java.util.Optional;

public interface BuildDao {
    Build save(Build build);
    Optional<Build> findById(int id);
    List<Build> findByUserId(int userId);
    void addComponent(int buildId, int componentId);
    List<Component> findComponentsByBuildId(int buildId);
}
