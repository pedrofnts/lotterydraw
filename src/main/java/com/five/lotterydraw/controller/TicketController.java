package com.five.lotterydraw.controller;

import com.five.lotterydraw.model.Ticket;
import com.five.lotterydraw.repository.TicketRepository;
import com.five.lotterydraw.repository.UserRepository;
import com.five.lotterydraw.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketService ticketService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> add(@PathVariable Long userId,
                                 @RequestBody Ticket ticket,
                                 @RequestParam(required = false) boolean generateNumbers)
    {
        try {
            Ticket savedTicket = ticketService.addTicket(userId, ticket, generateNumbers);
            return ResponseEntity.ok(savedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}