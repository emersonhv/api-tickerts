package com.rest.api_tickets.exception;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
