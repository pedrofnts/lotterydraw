package com.five.lotterydraw.service;

import com.five.lotterydraw.model.User;
import com.five.lotterydraw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


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




