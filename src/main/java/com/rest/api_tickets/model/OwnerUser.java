package com.rest.api_tickets.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
@DiscriminatorValue("OWNER")
public class OwnerUser extends User implements Owner, Serializable {
    // Atributos o métodos específicos para el dueño
}
