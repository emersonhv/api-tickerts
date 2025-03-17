package com.rest.api_tickets.service.impl;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.exception.InvalidDataException;
import com.rest.api_tickets.exception.ResourceNotFoundException;
import com.rest.api_tickets.mapper.TicketMapper;
import com.rest.api_tickets.model.Status;
import com.rest.api_tickets.model.Ticket;
import com.rest.api_tickets.model.User;
import com.rest.api_tickets.repository.TicketRepository;
import com.rest.api_tickets.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    @Autowired
    private final TicketRepository ticketRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TicketMapper ticketMapper;

    @Override
    @Transactional
    public TicketResponseDTO createTicket(TicketRequestDTO requestDTO) {
        if (requestDTO.getSubject() == null || requestDTO.getSubject().isEmpty()) {
            throw new InvalidDataException("El asunto del ticket no puede estar vacío");
        }

        User owner = userRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario dueño no encontrado con ID: " + requestDTO.getOwnerId()
                ));

        Ticket ticket = ticketMapper.toEntity(requestDTO);
        ticket.setOwner(owner);

        if (requestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Usuario asignado no encontrado con ID: " + requestDTO.getAssigneeId()
                    ));
            ticket.setAssignee(assignee);
        }

        ticket = ticketRepository.save(ticket);
        return ticketMapper.toResponseDTO(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado, ID: " + id));
        return ticketMapper.toResponseDTO(ticket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(ticketMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TicketResponseDTO updateTicket(Long id, TicketRequestDTO requestDTO) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        ticket.setSubject(requestDTO.getSubject());
        ticket.setDescription(requestDTO.getDescription());
        ticket.setStatus(requestDTO.getStatus());
        ticket.setPriority(requestDTO.getPriority());

        if (requestDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));
            ticket.setAssignee(assignee);
        }

        ticket = ticketRepository.save(ticket);

        return ticketMapper.toResponseDTO(ticket);
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketResponseDTO> getAllTickets(Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Ticket> ticketsPage;
        if (status != null) {
            ticketsPage = ticketRepository.findByStatus(status, pageable);
        } else {
            ticketsPage = ticketRepository.findAll(pageable);
        }

        return ticketsPage.map(ticketMapper::toResponseDTO);
    }
}
