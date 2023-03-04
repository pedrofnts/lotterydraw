package com.five.lotterydraw.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.five.lotterydraw.dto.TicketDto;
import com.five.lotterydraw.model.Ticket;
import com.five.lotterydraw.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void listAll() throws Exception {
        List<Ticket> ticketList = new ArrayList<>();
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setNumbers(List.of(1, 2, 3, 4, 5));
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setNumbers(List.of(6, 7, 8, 9, 10));
        ticketList.add(ticket1);
        ticketList.add(ticket2);

        when(ticketService.listAllByOwner(1L)).thenReturn(ticketList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ticketList)));
    }

    @Test
    void add() throws Exception {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setNumbers(List.of(1, 2, 3, 4, 5));

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setNumbers(ticketDto.getNumbers());

        when(ticketService.addTicket(eq(1L), any(Ticket.class))).thenReturn(ticket);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users/1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        Ticket createdTicket = objectMapper.readValue(response, Ticket.class);

        assert createdTicket.getId().equals(ticket.getId());
        assert createdTicket.getNumbers().equals(ticket.getNumbers());
    }
}
