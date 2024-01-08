package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.services.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return ResponseEntity.ok(ticketService
                .getAllTickets()
                .stream()
                .map(t -> modelMapper.map(t, TicketDto.class))
                .toList());
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
