package com.example.java.quiz5.unit;

import com.example.java.quiz5.entity.User;
import com.example.java.quiz5.repository.UserRepository;
import com.example.java.quiz5.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Mockito extension'ını ekliyoruz
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // UserRepository'i mock'lıyoruz

    @InjectMocks
    private UserServiceImpl userService;  // UserServiceImpl'i test edeceğiz

    @Test
    void testAddUser() {
        // Test verisi
        User user = new User(null, "John", "Doe", "password123");  // ID veritabanı tarafından otomatik atanacak

        // Mock: save metodunun kullanıcıyı döndürecek şekilde davranmasını sağlıyoruz
        when(userRepository.save(user)).thenReturn(user);

        // Service metodunu çağırıyoruz
        User savedUser = userService.addUser(user);

        // Sonuçları doğruluyoruz
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getName());
        assertEquals("Doe", savedUser.getSurname());
        assertEquals("password123", savedUser.getPassword());
    }

    @Test
    void testUpdateUser() {
        // Test verisi
        User existingUser = new User(1L, "John", "Doe", "password123");
        User updatedUser = new User(1L, "John", "Doe", "newpassword");

        // Mock: findById ve save metodunun doğru şekilde davranmasını sağlıyoruz
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Service metodunu çağırıyoruz
        User result = userService.updateUser(1L, updatedUser);

        // Sonuçları doğruluyoruz
        assertNotNull(result);
        assertEquals("newpassword", result.getPassword());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        // Mock: deleteById metodunun çağrılacağını varsayıyoruz
        doNothing().when(userRepository).deleteById(userId);

        // Service metodunu çağırıyoruz
        userService.deleteUser(userId);

        // verify: deleteById metodunun çağrıldığını doğruluyoruz
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUsers() {
        // Test verisi
        User user1 = new User(1L, "John", "Doe", "password123");
        User user2 = new User(2L, "Jane", "Smith", "password456");
        List<User> users = Arrays.asList(user1, user2);  // Kullanıcı listesini oluşturuyoruz

        // Mock: findAll metodunun kullanıcı listesini döndürecek şekilde davranmasını sağlıyoruz
        when(userRepository.findAll()).thenReturn(users);

        // Service metodunu çağırıyoruz
        List<User> result = userService.getUsers();

        // Sonuçları doğruluyoruz
        assertNotNull(result);
        assertEquals(2, result.size());  // Kullanıcı sayısının 2 olduğunu doğruluyoruz
        assertEquals("John", result.get(0).getName());  // İlk kullanıcının adı "John" olmalı
        assertEquals("Jane", result.get(1).getName());  // İkinci kullanıcının adı "Jane" olmalı
    }
}
