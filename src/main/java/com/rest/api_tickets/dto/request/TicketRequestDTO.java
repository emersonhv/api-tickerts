package com.rest.api_tickets.dto.request;

import lombok.Builder;
import lombok.Data;
import com.rest.api_tickets.model.Status;
import com.rest.api_tickets.model.Priority;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@Builder
public class TicketRequestDTO implements Serializable {

    private String subject;
    private String description;
    private Status status;
    private Priority priority;
    private Long ownerId;
    private Long assigneeId;
}
