package com.rest.api_tickets.dto.response;

import com.rest.api_tickets.model.Priority;
import com.rest.api_tickets.model.Status;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TicketResponseDTO implements Serializable {
    private Long id;
    private String subject;
    private String description;
    private Status status;
    private Priority priority;
    private UserResponseDTO owner;
    private UserResponseDTO assignee;
}
