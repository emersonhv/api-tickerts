package com.rest.api_tickets.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserResponseDTO implements Serializable {
    private Long id;
    private String name;
    private String email;
}
