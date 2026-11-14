package ru.itis.oris501.configuratorpc.dao;

import ru.itis.oris501.configuratorpc.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
    Optional<User> findById(int id);
    User save(User user);
}
