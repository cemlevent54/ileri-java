package com.example.java.quiz5.controller;

import com.example.java.quiz5.entity.User;
import com.example.java.quiz5.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Kullanıcıları getir", description = "Kullanıcıları getir")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Kullanıcı ekle", description = "Kullanıcı ekle")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return ResponseEntity.status(201).body(newUser);  // Yeni kullanıcı ile birlikte 201 Created döndürülür
    }

    @Operation(summary = "Kullanıcı güncelle", description = "Kullanıcı güncelle")
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);  // Güncellenmiş kullanıcıyı döndürür
        }
        return ResponseEntity.notFound().build();  // Kullanıcı bulunmazsa 404 döndürür
    }

    @Operation(summary = "Kullanıcı sil", description = "Kullanıcı sil")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        // Kullanıcıyı silmeden önce kullanıcıyı alıp dönebiliriz
        userService.deleteUser(id);
        return ResponseEntity.notFound().build();  // Kullanıcı bulunamazsa 404 döndürür
    }
}
