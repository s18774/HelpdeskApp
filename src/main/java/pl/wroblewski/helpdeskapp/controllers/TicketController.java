package pl.wroblewski.helpdeskapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createTicket(@RequestBody Ticket ticket) {
        ticketService.addTicket(ticket);
        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Ticket created!")
                .build(), HttpStatus.CREATED);
    }
}
