package com.rest.api_tickets.mapper;

import com.rest.api_tickets.dto.response.UserResponseDTO;
import com.rest.api_tickets.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDTO toResponseDTO(User user);
}
