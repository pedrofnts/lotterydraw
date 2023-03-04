package com.five.lotterydraw.service;

import com.five.lotterydraw.model.Ticket;
import com.five.lotterydraw.model.User;
import com.five.lotterydraw.repository.TicketRepository;
import com.five.lotterydraw.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TicketServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListAll() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<Ticket> result = ticketService.listAll();

        assertEquals(tickets, result);
    }

    @Test
    void testAddTicket() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Ticket ticket = new Ticket();
        ticket.setOwner(user);
        ticket.setNumbers(List.of(1, 2, 3, 4, 5));
        ticket.setRandomized(false);

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.addTicket(1L, ticket);

        assertEquals(ticket, result);
    }

    @Test
    void testAddTicketWithInvalidNumbers() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Ticket ticket = new Ticket();
        ticket.setOwner(user);
        ticket.setNumbers(List.of(1, 2, 3, 11, 5)); // Invalid number 11
        ticket.setRandomized(false);

        assertThrows(IllegalArgumentException.class, () -> ticketService.addTicket(1L, ticket));

        verify(ticketRepository, never()).save(any());
    }

    @Test
    void testAddTicketWithRandomizedNumbers() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Ticket ticket = new Ticket();
        ticket.setOwner(user);
        ticket.setRandomized(true);

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.addTicket(1L, ticket);

        assertNotNull(result.getNumbers());
        assertEquals(5, result.getNumbers().size());
    }

    @Test
    void testAddTicketWithMoreThanFiveRandomizedNumbers() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Ticket ticket = new Ticket();
        ticket.setOwner(user);
        ticket.setRandomized(true);

        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket result = ticketService.addTicket(1L, ticket);

        assertNotNull(result.getNumbers());
        assertTrue(result.getNumbers().size() <= 5);
    }


    @Test
    void testAddTicketWithRandomNumbers() {
        // arrange
        Long userId = 1L;
        Ticket ticket = new Ticket();
        ticket.setRandomized(true);

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // act
        Ticket result = ticketService.addTicket(userId, ticket);

        // assert
        assertNotNull(result.getNumbers());
        assertEquals(5, result.getNumbers().size());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testAddTicketWithNonRandomNumbers() {
        // arrange
        Long userId = 1L;
        Ticket ticket = new Ticket();
        ticket.setNumbers(List.of(1, 2, 3, 4, 5));
        ticket.setRandomized(false);

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // act
        Ticket result = ticketService.addTicket(userId, ticket);

        // assert
        assertEquals(ticket.getNumbers(), result.getNumbers());
        verify(ticketRepository).save(ticket);
    }

    @Test
    void testAddTicketWithMoreThanFiveNumbers() {
        // arrange
        Long userId = 1L;
        Ticket ticket = new Ticket();
        ticket.setNumbers(List.of(1, 2, 3, 4, 5, 6, 7));
        ticket.setRandomized(false);

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // act
        Ticket result = ticketService.addTicket(userId, ticket);

        // assert
        assertEquals(6.0, result.getPrice());
        verify(ticketRepository).save(ticket);
    }
}
