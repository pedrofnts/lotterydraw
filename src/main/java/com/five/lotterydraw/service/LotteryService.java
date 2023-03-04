package com.five.lotterydraw.service;

import com.five.lotterydraw.dto.WinnerTicketDto;
import com.five.lotterydraw.model.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LotteryService {

    private final TicketService ticketService;

    protected List<Integer> generateWinningNumbers() {
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();
        while (uniqueNumbers.size() < 5) {
            int number = random.nextInt(10) + 1;
            uniqueNumbers.add(number);
        }

        return new ArrayList<>(uniqueNumbers);
    }

    public List<WinnerTicketDto> findWinningTickets() {
        List<WinnerTicketDto> winningTickets = new ArrayList<>();
        List<Ticket> tickets = ticketService.listAll();
        List<Integer> winningNumbers = generateWinningNumbers();
        double prize = calculatePrize();

        for (Ticket ticket : tickets) {
            if (new HashSet<>(ticket.getNumbers()).containsAll(winningNumbers)) {
                winningTickets.add(new WinnerTicketDto(ticket));
            }
        }

        double prizePerTicket = prize / winningTickets.size();

        return winningTickets.stream()
                .peek(ticket -> ticket.setPrize(prizePerTicket))
                .collect(Collectors.toList());
    }

    public double calculatePrize() {
        List<Ticket> tickets = ticketService.listAll();

        double totalTicketPrice = tickets.stream()
                .mapToDouble(Ticket::getPrice)
                .sum();

        return totalTicketPrice * 0.85;
    }


}



