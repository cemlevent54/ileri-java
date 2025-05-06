package com.example.java.quiz5.interfaces;

import com.example.java.quiz5.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User addUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
