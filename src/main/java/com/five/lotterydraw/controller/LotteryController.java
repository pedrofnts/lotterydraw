package com.five.lotterydraw.controller;

import com.five.lotterydraw.dto.WinnerTicketDto;
import com.five.lotterydraw.service.LotteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lottery")
@RequiredArgsConstructor
public class LotteryController {

    private final LotteryService lotteryService;

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
