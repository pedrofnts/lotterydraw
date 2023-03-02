package com.five.lotterydraw.controller;

import com.five.lotterydraw.model.User;
import com.five.lotterydraw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody User user) {
        return userRepository.save(user);
    }

}
