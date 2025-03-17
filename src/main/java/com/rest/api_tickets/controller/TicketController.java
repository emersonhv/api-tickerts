package com.rest.api_tickets.controller;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.model.Status;
import com.rest.api_tickets.service.impl.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TicketController {

    @Autowired
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Crea un nuevo ticket.
     *
     * @param requestDTO Datos del ticket a crear.
     * @return Ticket creado en formato DTO.
     */
    @PostMapping(value = "ticket")
    public ResponseEntity<TicketResponseDTO> createTicket(@Valid @RequestBody TicketRequestDTO requestDTO) {
        TicketResponseDTO responseDTO = ticketService.createTicket(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Obtiene un ticket por su ID.
     *
     * @param id ID del ticket a buscar.
     * @return Ticket encontrado en formato DTO.
     */
    @GetMapping(value = "ticket/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        TicketResponseDTO responseDTO = ticketService.getTicketById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Obtiene todos los tickets.
     *
     * @return Lista de tickets en formato DTO.
     */
    @GetMapping(value = "tickets")
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        List<TicketResponseDTO> responseDTOs = ticketService.getAllTickets();
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    /**
     * Actualiza un ticket existente.
     *
     * @param id         ID del ticket a actualizar.
     * @param requestDTO Datos actualizados del ticket.
     * @return Ticket actualizado en formato DTO.
     */
    @PutMapping("ticket/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketRequestDTO requestDTO) {
        TicketResponseDTO responseDTO = ticketService.updateTicket(id, requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Elimina un ticket por su ID.
     *
     * @param id ID del ticket a eliminar.
     * @return Respuesta vacía con código HTTP 204 (No Content).
     */
    @DeleteMapping("ticket/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Obtiene todos los tickets paginados, con o sin status.
     *
     * @param status Estatus del ticket (opcional).
     * @param page   Número de página (comienza en 0, por defecto 0).
     * @param size   Tamaño de la página (por defecto 10).
     * @return Página de tickets en formato DTO.
     */
    @GetMapping("tickets/filtered")
    public ResponseEntity<Page<TicketResponseDTO>> getAllTickets(
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TicketResponseDTO> ticketsPage = ticketService.getAllTickets(status, page, size);
        return new ResponseEntity<>(ticketsPage, HttpStatus.OK);
    }
}
