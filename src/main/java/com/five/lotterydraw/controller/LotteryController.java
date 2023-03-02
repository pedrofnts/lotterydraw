package com.five.lotterydraw.controller;

import com.five.lotterydraw.dto.WinnerTicketDto;
import com.five.lotterydraw.service.LotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/draw")
    public ResponseEntity<?> draw() {
        try {
            List<WinnerTicketDto> draw = lotteryService.findWinningTickets();
            return ResponseEntity.ok(draw);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
