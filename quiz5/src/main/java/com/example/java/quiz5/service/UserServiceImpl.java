package com.example.java.quiz5.service;

import com.example.java.quiz5.entity.User;
import com.example.java.quiz5.interfaces.UserService;
import com.example.java.quiz5.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public List<User> getUsers() {
        log.info("Kullanıcıları getir");
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User addUser(User user) {
        log.info("Kullanıcı ekle");
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User user) {
        log.info("Kullanıcı güncelle");
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User u = existingUser.get();
            u.setName(user.getName());
            u.setSurname(user.getSurname());
            u.setPassword(user.getPassword());
            return userRepository.save(u);
        }
        return null;
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        log.info("Kullanıcı sil");
        userRepository.deleteById(id);
    }
}
