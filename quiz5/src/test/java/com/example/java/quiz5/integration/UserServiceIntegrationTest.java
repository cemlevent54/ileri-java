package com.example.java.quiz5.integration;

import com.example.java.quiz5.entity.User;
import com.example.java.quiz5.repository.UserRepository;
import com.example.java.quiz5.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest  // Spring Boot konteyneriyle entegrasyon testi yapılır
public class UserServiceIntegrationTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testAddUser() {
        // Test verisi
        User user = new User(null, "John", "Doe", "password123");

        // Kullanıcıyı veritabanına ekliyoruz
        User savedUser = userService.addUser(user);

        // Sonuçları doğruluyoruz
        assertNotNull(savedUser);  // Kullanıcı kaydedildi mi?
        assertNotNull(savedUser.getId());  // ID'nin atanıp atanmadığını kontrol ediyoruz (auto-generated)
        assertEquals("John", savedUser.getName());  // Kullanıcı adı doğrulaması
        assertEquals("Doe", savedUser.getSurname());  // Kullanıcı soyadı doğrulaması
    }

    @Test
    @Transactional
    void testUpdateUser() {
        // Test verisi
        User user = new User(null, "John", "Doe", "password123");
        User savedUser = userService.addUser(user);  // Kullanıcıyı önce ekliyoruz

        // Güncelleme işlemi
        savedUser.setPassword("newpassword");
        User updatedUser = userService.updateUser(savedUser.getId(), savedUser);  // Kullanıcıyı güncelliyoruz

        // Sonuçları doğruluyoruz
        assertNotNull(updatedUser);  // Güncellenen kullanıcıyı kontrol ediyoruz
        assertEquals("newpassword", updatedUser.getPassword());  // Güncellenmiş şifreyi doğruluyoruz
    }

    @Test
    @Transactional
    void testDeleteUser() {
        // Test verisi
        User user = new User(null, "John", "Doe", "password123");
        User savedUser = userService.addUser(user);  // Kullanıcıyı önce ekliyoruz

        // Kullanıcıyı siliyoruz
        userService.deleteUser(savedUser.getId());

        // Silinen kullanıcının veritabanında olmadığını kontrol ediyoruz
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());  // Kullanıcı artık veritabanında olmamalı
    }

    @Test
    @Transactional
    void testGetUsers() {
        // Test verisi
        User user1 = new User(null, "John", "Doe", "password123");
        User user2 = new User(null, "Jane", "Smith", "password456");
        userService.addUser(user1);  // İlk kullanıcıyı ekliyoruz
        userService.addUser(user2);  // İkinci kullanıcıyı ekliyoruz

        // Kullanıcıları getiriyoruz
        List<User> users = userService.getUsers();

        // Sonuçları doğruluyoruz
        assertNotNull(users);  // Kullanıcılar listesi null olmamalı
        assertEquals(2, users.size());  // Listede iki kullanıcı olmalı
        assertEquals("John", users.get(0).getName());  // İlk kullanıcının adı "John" olmalı
        assertEquals("Jane", users.get(1).getName());  // İkinci kullanıcının adı "Jane" olmalı
    }
}
