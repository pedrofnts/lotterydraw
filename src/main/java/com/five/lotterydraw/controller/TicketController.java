package com.five.lotterydraw.controller;

import com.five.lotterydraw.model.Ticket;
import com.five.lotterydraw.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}")
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/tickets")
    public ResponseEntity<?> listAll(@PathVariable Long userId)
    {
        try {
            return ResponseEntity.ok(ticketService.listAllByOwner(userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/tickets")
    public ResponseEntity<?> add(@PathVariable Long userId,
                                 @RequestBody Ticket ticket)
    {
        try {
            Ticket savedTicket = ticketService.addTicket(userId, ticket);
            return ResponseEntity.ok(savedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}