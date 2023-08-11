package org.example.service;

import org.example.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByUsername(String username);
}
