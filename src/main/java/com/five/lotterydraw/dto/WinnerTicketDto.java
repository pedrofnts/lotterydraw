package com.five.lotterydraw.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.five.lotterydraw.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WinnerTicketDto {

    @JsonIgnore
    private Long id;
    private Long owner_id;
    private String owner_name;
    private List<Integer> numbers;
    private double prize;

    public WinnerTicketDto(Ticket ticket) {
        this.numbers = ticket.getNumbers();
        this.owner_id = ticket.getOwner().getId();
        this.owner_name = ticket.getOwner().getName();
        this.prize = ticket.getPrize();
    }

}
