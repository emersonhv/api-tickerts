package com.rest.api_tickets.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("ASSIGNEE")
public class AssigneeUser extends User implements Assignee, Serializable {
    // Atributos o métodos específicos para el asignado
}
