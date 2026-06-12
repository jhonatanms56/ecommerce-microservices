package com.ecommerce.controller;

import com.ecommerce.entity.User;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if (userRepository.findById(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
