package com.rest.api_tickets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String subject;

    @Column(nullable = false, length = 500)
    private String description;

    @CreationTimestamp
    @JsonIgnore
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDate creationDate;

    @UpdateTimestamp
    @JsonIgnore
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, columnDefinition = "int default 0")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
}
