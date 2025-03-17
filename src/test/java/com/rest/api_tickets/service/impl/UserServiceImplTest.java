package com.rest.api_tickets.service.impl;

import com.rest.api_tickets.dto.response.UserResponseDTO;
import com.rest.api_tickets.exception.ResourceNotFoundException;
import com.rest.api_tickets.mapper.UserMapper;
import com.rest.api_tickets.model.OwnerUser;
import com.rest.api_tickets.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private OwnerUser user;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    public void setUp() {
        user = new OwnerUser();
        user.setId(1L);
        user.setName("Juan Pérez");
        user.setEmail("juan.perez@example.com");

        userResponseDTO = UserResponseDTO.builder()
                .id(1L)
                .name("Juan Pérez")
                .email("juan.perez@example.com")
                .build();
    }

    @Test
    public void getUserByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO response = userService.getUserById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Juan Pérez", response.getName());

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getUserById_NotFoundTest() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(999L));

        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    public void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toResponseDTO(user)).thenReturn(userResponseDTO);

        List<UserResponseDTO> responseList = userService.getAllUsers();

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("Juan Pérez", responseList.get(0).getName());

        verify(userRepository, times(1)).findAll();
    }
}