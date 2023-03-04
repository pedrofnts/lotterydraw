package com.five.lotterydraw.repository;

import com.five.lotterydraw.model.Ticket;
import com.five.lotterydraw.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {
    @Mock
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // define o comportamento esperado para o método findAll()
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        when(ticketRepository.findAll()).thenReturn(tickets);

        // chama o método findAll() no mock
        List<Ticket> result = ticketRepository.findAll();

        // verifica se o comportamento esperado ocorreu
        assertEquals(tickets, result);
    }

    @Test
    public void testFindById() {
        // define o comportamento esperado para o método findById()
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        // chama o método findById() no mock
        Optional<Ticket> result = ticketRepository.findById(1L);

        // verifica se o comportamento esperado ocorreu
        assertTrue(result.isPresent());
        assertEquals(ticket, result.get());
    }

    @Test
    public void testSave() {
        User owner = new User();
        owner.setId(1L);
        owner.setName("Alice");

        // Cria o array de números do bilhete
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Cria o bilhete a ser salvo
        Ticket ticket = new Ticket();
        ticket.setNumbers(numbers);
        ticket.setOwner(owner);

        // Define o comportamento esperado para o método save()
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        // Chama o método save() no mock
        Ticket result = ticketRepository.save(ticket);

        // Verifica se o comportamento esperado ocorreu
        assertEquals(ticket, result);
    }

    @Test
    public void testFindAllByOwner_Id() {
        User user = new User();
        user.setId(1L);

        // define o comportamento esperado para o método findAllByOwner_Id()
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setOwner(user);
        tickets.add(ticket);
        when(ticketRepository.findAllByOwner_Id(1L)).thenReturn(tickets);

        // chama o método findAllByOwner_Id() no mock
        List<Ticket> result = ticketRepository.findAllByOwner_Id(1L);

        // verifica se o comportamento esperado ocorreu
        assertEquals(tickets, result);
    }
}