package com.five.lotterydraw.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @ElementCollection
    @CollectionTable(name = "ticket_numbers", joinColumns = @JoinColumn(name = "ticket_id"))
    private List<Integer> numbers = new ArrayList<>();

    @Column(name = "price")
    private double price;

    @Column
    @JsonProperty("randomized")
    private boolean randomized;

}
