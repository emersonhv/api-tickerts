package com.rest.api_tickets.controller;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.model.Status;
import com.rest.api_tickets.service.impl.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Crear un ticket", description = "Crea un nuevo ticket con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
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
    @Operation(summary = "Obtener un ticket por ID", description = "Recupera un ticket específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
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
    @Operation(summary = "Obtener todos los tickets", description = "Recupera una lista de todos los tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tickets recuperada exitosamente")
    })
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
    @Operation(summary = "Actualizar un ticket", description = "Actualiza un ticket existente con los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
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
    @Operation(summary = "Eliminar un ticket", description = "Elimina un ticket por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
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
    @Operation(summary = "Obtener tickets paginados y filtrados", description = "Recupera una página de tickets, opcionalmente filtrados por estatus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de tickets recuperada exitosamente")
    })
    @GetMapping("tickets/filtered")
    public ResponseEntity<Page<TicketResponseDTO>> getAllTickets(
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TicketResponseDTO> ticketsPage = ticketService.getAllTickets(status, page, size);
        return new ResponseEntity<>(ticketsPage, HttpStatus.OK);
    }
}
