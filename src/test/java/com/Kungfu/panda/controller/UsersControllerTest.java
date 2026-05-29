package com.Kungfu.panda.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.Kungfu.panda.entity.Users;
import com.Kungfu.panda.exception.DatabaseException;
import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.helper.UsersHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersHelper usersHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllUsers() throws Exception {
        // Given
        Users user1 = new Users();
        user1.setUser_id(1L);
        user1.setName("John Doe");
        user1.setEmail("john@example.com");

        Users user2 = new Users();
        user2.setUser_id(2L);
        user2.setName("Jane Doe");
        user2.setEmail("jane@example.com");
        List<Users> users = Arrays.asList(user1, user2);
        when(usersHelper.getAllUsers()).thenReturn(users);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());

        verify(usersHelper, times(2)).getAllUsers();

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void testGetUserById_Success() throws Exception {
        // Given
        Users user = new Users();
        user.setUser_id(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        when(usersHelper.getUserById(1L)).thenReturn(Optional.of(user));

        // When & Then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        // Given
        when(usersHelper.getUserById(1L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("User not found with ID: 1"));
    }

    @Test
    void testCreateUser() throws Exception {
        // Given
        Users user = new Users();
        user.setName("John Doe");
        user.setEmail("john@example.com");

        Users savedUser = new Users();
        savedUser.setUser_id(1L);
        savedUser.setName("John Doe");
        savedUser.setEmail("john@example.com");

        when(usersHelper.saveUser(any(Users.class))).thenReturn(savedUser);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetAllUsers_DatabaseException() throws Exception {
        // Given
        when(usersHelper.getAllUsers()).thenThrow(new DatabaseException("Database error"));

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Database error"));
    }
}
