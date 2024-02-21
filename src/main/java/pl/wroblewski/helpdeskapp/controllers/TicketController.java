package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.TicketCreateDto;
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
    public ResponseEntity<List<TicketDto>> getTickets(@PathParam("ticketId") Integer ticketId, @PathParam("userId") Integer userId, @PathParam("slaId") Integer slaId) {
        return ResponseEntity.ok(ticketService
                .getTickets(ticketId, userId, slaId)
                .stream()
                .map(t -> modelMapper.map(t, TicketDto.class))
                .toList());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createTicket(@RequestBody TicketCreateDto ticket) {
        ticketService.addTicket(modelMapper.map(ticket, Ticket.class));
        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Ticket created!")
                .build(), HttpStatus.CREATED);
    }
}
