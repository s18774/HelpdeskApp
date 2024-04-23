package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.TicketCreateDto;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.TicketService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class TicketController extends BaseController {
    private final TicketService ticketService;
    private final UserService userService;
    private final ModelMapper modelMapper;



    @GetMapping
    public ResponseEntity<List<TicketDto>> getTickets(@PathParam("ticketId") Integer ticketId, @PathParam("userId") Integer userId, @PathParam("slaId") Integer slaId, @AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(ticketService
                .getTickets(ticketId, userId, slaId, author.getUserId())
                .stream()
                .map(t -> modelMapper.map(t, TicketDto.class))
                .toList());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createTicket(@RequestBody TicketCreateDto ticket, @AuthenticationPrincipal UserDetails userDetails) throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        ticketService.addTicket(ticket.getSlaId(), ticket.getDepartmentId(), ticket.getFloor(),
                ticket.getTitle(), ticket.getDescription(), ticket.getUserId(), author.getUserId(),
                ticket.getHelpdeskId(), ticket.getGroupId());

        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Ticket created!")
                .build(), HttpStatus.CREATED);
    }
}
