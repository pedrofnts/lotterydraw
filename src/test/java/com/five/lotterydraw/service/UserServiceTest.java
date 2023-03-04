package com.five.lotterydraw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.jupiter.api.Test;

import com.five.lotterydraw.model.User;
import com.five.lotterydraw.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private UserService userService;

    @Test
    public void testListAll() {
        // define o comportamento esperado para o método findAll() do userRepository
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        // chama o método listAll() do userService
        List<User> result = userService.listAll();

        // verifica se o comportamento esperado ocorreu
        assertEquals(users, result);
    }

    @Test
    public void testGet() {
        // define o comportamento esperado para o método findById() do userRepository
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // chama o método get() do userService
        User result = userService.get(1L);

        // verifica se o comportamento esperado ocorreu
        assertEquals(user, result);
    }

    @Test
    public void testAdd() {
        // define o comportamento esperado para o método save() do userRepository
        User user = new User();
        user.setName("Alice");
        when(userRepository.save(user)).thenReturn(user);

        // chama o método add() do userService
        User result = userService.add(user);

        // verifica se o comportamento esperado ocorreu
        assertEquals(user, result);
    }

}
