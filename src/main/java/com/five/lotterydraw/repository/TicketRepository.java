package com.five.lotterydraw.repository;

import com.five.lotterydraw.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByOwner_Id(Long id);
}
