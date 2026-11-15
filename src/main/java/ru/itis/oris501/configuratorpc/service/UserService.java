package ru.itis.oris501.configuratorpc.service;

import ru.itis.oris501.configuratorpc.dao.UserDao;
import ru.itis.oris501.configuratorpc.dao.impl.UserDaoJdbc;
import ru.itis.oris501.configuratorpc.model.User;
import ru.itis.oris501.configuratorpc.util.PasswordHasher;

import java.util.Optional;

public class UserService {
    private final UserDao userDao;

    public UserService() {
        this(new UserDaoJdbc());
    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User registration(String username, String rawPassword) {
        if (username == null || username.trim().length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("ник должен быть длиной от 3 до 50 символов");
        }

        if (rawPassword == null || rawPassword.length() < 6) {
            throw new IllegalArgumentException("пароль должен состоять из минимум 6 символов");
        }

        if (userDao.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("ник уже занят");
        }

        String salt = PasswordHasher.generateSalt(16);
        String hashPassword = PasswordHasher.hash(rawPassword, salt);
        User user = new User(username, hashPassword, "USER", salt);
        return userDao.save(user);
    }

    public Optional<User> login(String username, String rawPassword) {
        if (username == null || rawPassword == null) {
            return Optional.empty();
        }

        Optional<User> userOpt = userDao.findByUsername(username);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();
        if (PasswordHasher.matches(rawPassword, user.getSalt(), user.getPasswordHash())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

}
