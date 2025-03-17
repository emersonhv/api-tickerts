package com.rest.api_tickets.controller;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.model.Priority;
import com.rest.api_tickets.model.Status;
import com.rest.api_tickets.service.impl.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private TicketResponseDTO ticketResponseDTO;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        ticketResponseDTO = TicketResponseDTO.builder()
                .id(1L)
                .subject("Problema con el servidor")
                .description("El servidor no responde desde hace 2 horas.")
                .status(Status.OPEN)
                .priority(Priority.HIGH)
                .build();
    }

    @Test
    public void createTicketTest() throws Exception {
        // Configurar mocks
        when(ticketService.createTicket(any(TicketRequestDTO.class))).thenReturn(ticketResponseDTO);

        // Ejecutar y verificar la solicitud
        mockMvc.perform(post("/api/v1/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\": \"Problema con el servidor\", \"description\": \"El servidor no responde desde hace 2 horas.\", \"status\": \"OPEN\", \"priority\": 3, \"ownerId\": 1, \"assigneeId\": 2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Problema con el servidor"));

        // Verificar interacciones con los mocks
        verify(ticketService, times(1)).createTicket(any(TicketRequestDTO.class));
    }

    @Test
    public void getTicketByIdTest() throws Exception {
        // Configurar mocks
        when(ticketService.getTicketById(1L)).thenReturn(ticketResponseDTO);

        // Ejecutar y verificar la solicitud
        mockMvc.perform(get("/api/v1/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Problema con el servidor"));

        // Verificar interacciones con los mocks
        verify(ticketService, times(1)).getTicketById(1L);
    }

    @Test
    public void getAllTicketsTest() throws Exception {
        // Configurar mocks
        when(ticketService.getAllTickets()).thenReturn(Collections.singletonList(ticketResponseDTO));

        // Ejecutar y verificar la solicitud
        mockMvc.perform(get("/api/v1/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].subject").value("Problema con el servidor"));

        // Verificar interacciones con los mocks
        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    public void updateTicketTest() throws Exception {
        // Configurar mocks
        when(ticketService.updateTicket(eq(1L), any(TicketRequestDTO.class))).thenReturn(ticketResponseDTO);

        // Ejecutar y verificar la solicitud
        mockMvc.perform(put("/api/v1/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\": \"Problema con el servidor\", \"description\": \"El servidor no responde desde hace 2 horas.\", \"status\": \"OPEN\", \"priority\": 3, \"ownerId\": 1, \"assigneeId\": 2}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Problema con el servidor"));

        // Verificar interacciones con los mocks
        verify(ticketService, times(1)).updateTicket(eq(1L), any(TicketRequestDTO.class));
    }

    @Test
    public void deleteTicketTest() throws Exception {
        // Ejecutar y verificar la solicitud
        mockMvc.perform(delete("/api/v1/ticket/1"))
                .andExpect(status().isNoContent());

        // Verificar interacciones con los mocks
        verify(ticketService, times(1)).deleteTicket(1L);
    }

    @Test
    public void getAllTicketsFilteredTest() throws Exception {
        // Configurar mocks
        Page<TicketResponseDTO> ticketPage = new PageImpl<>(Collections.singletonList(ticketResponseDTO), PageRequest.of(0, 10), 1);
        when(ticketService.getAllTickets(any(Status.class), eq(0), eq(10))).thenReturn(ticketPage);

        // Ejecutar y verificar la solicitud
        mockMvc.perform(get("/api/v1/tickets/filtered?status=OPEN&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].subject").value("Problema con el servidor"));

        // Verificar interacciones con los mocks
        verify(ticketService, times(1)).getAllTickets(any(Status.class), eq(0), eq(10));
    }
}