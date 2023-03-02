package com.five.lotterydraw.repository;

import com.five.lotterydraw.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
