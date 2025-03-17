package com.rest.api_tickets.mapper;

import com.rest.api_tickets.dto.request.TicketRequestDTO;
import com.rest.api_tickets.dto.response.TicketResponseDTO;
import com.rest.api_tickets.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Ticket toEntity(TicketRequestDTO ticketRequestDTO);

    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "assignee", target = "assignee")
    TicketResponseDTO toResponseDTO(Ticket ticket);
}
