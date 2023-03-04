package com.five.lotterydraw.service;

import com.five.lotterydraw.model.Ticket;
import com.five.lotterydraw.model.User;
import com.five.lotterydraw.repository.TicketRepository;
import com.five.lotterydraw.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class TicketService {

    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    public List<Ticket> listAll() {
        return ticketRepository.findAll();
    }

    public List<Ticket> listAllByOwner(Long userId) {
        return ticketRepository.findAllByOwner_Id(userId);
    }

    public Ticket addTicket(Long userId, Ticket ticket) {
        User user = userRepository.findById(userId).orElseThrow();

        ticket.setOwner(user);

        if (ticket.isRandomized()) {
            generateTicketNumbers(ticket);
        }

        List<Integer> numbers = ticket.getNumbers();
        for (Integer number : numbers) {
            if (number < 1 || number > 10) {
                throw new IllegalArgumentException("Os números da aposta devem ser de 1 a 10.");
            }
        }

        double price = calculateTicketPrice(ticket);
        ticket.setPrice(price);

        return ticketRepository.save(ticket);
    }

    private void generateTicketNumbers(Ticket ticket) {
        List<Integer> numbers = new ArrayList<>();
        Random random = new Random();

        while (numbers.size() < 5) {
            int number = random.nextInt(10) + 1;
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }
        ticket.setNumbers(numbers);
    }

    private double calculateTicketPrice(Ticket ticket) {
        int numNumbers = ticket.getNumbers().size();
        double price = 5.0;
        if (numNumbers > 5) {
            price = price * (1.0 + 0.1 * (numNumbers - 5));
        }
        return price;
    }
}