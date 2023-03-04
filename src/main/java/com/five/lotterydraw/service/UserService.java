package com.five.lotterydraw.service;

import com.five.lotterydraw.model.User;
import com.five.lotterydraw.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User add(User user) {
        return userRepository.save(user);
    }

    }




