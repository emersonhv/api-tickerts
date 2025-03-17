package com.rest.api_tickets.repository;


import com.rest.api_tickets.model.Status;
import com.rest.api_tickets.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Busca tickets paginados por estatus.
     *
     * @param status   Estatus del ticket.
     * @param pageable Configuración de paginación.
     * @return Página de tickets.
     */
    Page<Ticket> findByStatus(Status status, Pageable pageable);

    /**
     * Busca todos los tickets paginados.
     *
     * @param pageable Configuración de paginación.
     * @return Página de tickets.
     */
    Page<Ticket> findAll(Pageable pageable);
}
