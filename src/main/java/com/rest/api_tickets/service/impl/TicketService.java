package com.rest.api_tickets.service.impl;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.model.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketService {

    /**
     * Crea un nuevo ticket.
     *
     * @param requestDTO Datos del ticket a crear.
     * @return Ticket creado en formato DTO.
     */
    TicketResponseDTO createTicket(TicketRequestDTO requestDTO);

    /**
     * Obtiene un ticket por su ID.
     *
     * @param id ID del ticket a buscar.
     * @return Ticket encontrado en formato DTO.
     */
    TicketResponseDTO getTicketById(Long id);

    /**
     * Obtiene todos los tickets.
     *
     * @return Lista de tickets en formato DTO.
     */
    List<TicketResponseDTO> getAllTickets();

    /**
     * Actualiza un ticket existente.
     *
     * @param id         ID del ticket a actualizar.
     * @param requestDTO Datos actualizados del ticket.
     * @return Ticket actualizado en formato DTO.
     */
    TicketResponseDTO updateTicket(Long id, TicketRequestDTO requestDTO);

    /**
     * Elimina un ticket por su ID.
     *
     * @param id ID del ticket a eliminar.
     */
    void deleteTicket(Long id);

    /**
     * Obtiene todos los tickets paginados por estatus.
     *
     * @param status   Estatus del ticket
     * @param page     Número de página (comienza en 0).
     * @param size     Tamaño de la página.
     * @return Página de tickets en formato DTO.
     */
    Page<TicketResponseDTO> getAllTickets(Status status, int page, int size);
}
