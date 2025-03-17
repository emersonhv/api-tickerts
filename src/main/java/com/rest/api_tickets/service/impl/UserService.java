package com.rest.api_tickets.service.impl;

import com.rest.api_tickets.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return Usuario encontrado en formato DTO.
     */
    UserResponseDTO getUserById(Long id);

    /**
     * Obtiene todos los usuario.
     *
     * @return Lista de usuarios en formato DTO.
     */
    List<UserResponseDTO> getAllUsers();
}
