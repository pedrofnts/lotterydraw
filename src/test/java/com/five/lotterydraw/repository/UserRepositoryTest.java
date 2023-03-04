package com.five.lotterydraw.repository;

import com.five.lotterydraw.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // define o comportamento esperado para o método findAll()
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        // chama o método findAll() no mock
        List<User> result = userRepository.findAll();

        // verifica se o comportamento esperado ocorreu
        assertEquals(users, result);
    }

    @Test
    public void testFindById() {
        // define o comportamento esperado para o método findById()
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // chama o método findById() no mock
        Optional<User> result = userRepository.findById(1L);

        // verifica se o comportamento esperado ocorreu
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testSave() {
        // define o comportamento esperado para o método save()
        User user = new User();
        user.setName("Alice");
        when(userRepository.save(user)).thenReturn(user);

        // chama o método save() no mock
        User result = userRepository.save(user);

        // verifica se o comportamento esperado ocorreu
        assertEquals(user, result);
    }

}

