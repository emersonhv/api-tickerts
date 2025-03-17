package com.rest.api_tickets.service.impl;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.exception.ResourceNotFoundException;
import com.rest.api_tickets.mapper.TicketMapper;
import com.rest.api_tickets.mapper.UserMapper;
import com.rest.api_tickets.model.*;
import com.rest.api_tickets.repository.TicketRepository;
import com.rest.api_tickets.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private TicketServiceImpl ticketService;
    private TicketRequestDTO ticketRequestDTO;
    private Ticket ticket;
    private TicketResponseDTO ticketResponseDTO;
    private User owner;
    private User assignee;

    @BeforeEach
    public void setUp() {
        owner = new OwnerUser();
        owner.setId(1L);
        owner.setName("Juan Pérez");
        owner.setEmail("juan.perez@example.com");

        assignee = new AssigneeUser();
        assignee.setId(2L);
        assignee.setName("Ana Gómez");
        assignee.setEmail("ana.gomez@example.com");

        ticketRequestDTO = TicketRequestDTO.builder()
                .subject("Problema con el servidor")
                .description("El servidor no responde desde hace 2 horas.")
                .status(Status.OPEN)
                .priority(Priority.HIGH)
                .ownerId(1L)
                .assigneeId(2L)
                .build();

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setSubject("Problema con el servidor");
        ticket.setDescription("El servidor no responde desde hace 2 horas.");
        ticket.setCreationDate(LocalDate.now());
        ticket.setUpdateDate(LocalDate.now());
        ticket.setStatus(Status.OPEN);
        ticket.setPriority(Priority.HIGH);
        ticket.setOwner(owner);
        ticket.setAssignee(assignee);

        ticketResponseDTO = TicketResponseDTO.builder()
                .id(1L)
                .subject("Problema con el servidor")
                .description("El servidor no responde desde hace 2 horas.")
                .status(Status.OPEN)
                .priority(Priority.HIGH)
                .owner(userMapper.toResponseDTO(owner))
                .assignee(userMapper.toResponseDTO(assignee))
                .build();
    }

    @Test
    public void createTicketTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(userRepository.findById(2L)).thenReturn(Optional.of(assignee));
        when(ticketMapper.toEntity(ticketRequestDTO)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toResponseDTO(ticket)).thenReturn(ticketResponseDTO);

        TicketResponseDTO response = ticketService.createTicket(ticketRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Problema con el servidor", response.getSubject());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(2L);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void getTicketByIdTest() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketMapper.toResponseDTO(ticket)).thenReturn(ticketResponseDTO);

        TicketResponseDTO response = ticketService.getTicketById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Problema con el servidor", response.getSubject());

        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    public void getTicketById_NotFoundTest() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicketById(999L));

        verify(ticketRepository, times(1)).findById(999L);
    }

    @Test
    public void getAllTicketsTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Ticket> ticketPage = new PageImpl<>(Collections.singletonList(ticket), pageable, 1);
        when(ticketRepository.findAll(pageable)).thenReturn(ticketPage);
        when(ticketMapper.toResponseDTO(ticket)).thenReturn(ticketResponseDTO);

        Page<TicketResponseDTO> responsePage = ticketService.getAllTickets(null, 0, 10);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        assertEquals("Problema con el servidor", responsePage.getContent().get(0).getSubject());

        verify(ticketRepository, times(1)).findAll(pageable);
    }
}