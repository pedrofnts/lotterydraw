package com.five.lotterydraw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.lotterydraw.dto.UserDto;
import com.five.lotterydraw.model.User;
import com.five.lotterydraw.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAll() throws Exception {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");
        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");
        userList.add(user1);
        userList.add(user2);

        when(userService.listAll()).thenReturn(userList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userList)));
    }

    @Test
    void get() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("User");

        when(userService.get(userId)).thenReturn(user);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void add() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("User");

        User user = new User();
        user.setId(1L);
        user.setName(userDto.getName());

        when(userService.add(any(User.class))).thenReturn(user);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        User createdUser = objectMapper.readValue(response, User.class);

        assert createdUser.getId().equals(user.getId());
        assert createdUser.getName().equals(user.getName());
    }
}
