package com.five.lotterydraw.service;

import com.five.lotterydraw.dto.WinnerTicketDto;
import com.five.lotterydraw.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LotteryService {

    @Autowired
    private TicketService ticketService;

    private List<Integer> generateWinningNumbers() {
        Set<Integer> uniqueNumbers = new HashSet<>();
        Random random = new Random();
        while (uniqueNumbers.size() < 5) {
            int number = random.nextInt(10) + 1;
            uniqueNumbers.add(number);
        }
        List<Integer> uniqueNumbersList = new ArrayList<>(uniqueNumbers);
        log.info("Winning numbers: " + uniqueNumbersList);
        return uniqueNumbersList;
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



