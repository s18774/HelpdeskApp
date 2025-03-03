package pl.wroblewski.helpdeskapp.controllers;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.ticket.TicketCreateDto;
import pl.wroblewski.helpdeskapp.dto.ticket.TicketDto;
import pl.wroblewski.helpdeskapp.dto.ticket.TicketUpdateDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.services.TicketService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@CrossOrigin( origins = {"https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io",
        "http://localhost:3000"})
@RequiredArgsConstructor
public class TicketController extends BaseController {
    private final TicketService ticketService;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<TicketDto>> getTickets(@PathParam("ticketNumber") Integer ticketNumber,
                                                      @PathParam("userId") Integer userId,
                                                      @PathParam("slaId") Integer slaId,
                                                      @PathParam("stageId") Integer stageId,
                                                      @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(ticketService
                .getTickets(ticketNumber, userId, slaId, stageId, author.getUserId())
                .stream()
                .map(t -> modelMapper.map(t, TicketDto.class))
                .toList());
    }

    @GetMapping("{ticketId}")
    public ResponseEntity<TicketDto> getTicket(@PathVariable Integer ticketId,
                                               @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        UserTicket ticket = ticketService.getTicket(ticketId, author.getUserId());
        return ResponseEntity.ok(modelMapper.map(ticket, TicketDto.class));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createTicket(@RequestBody @Valid TicketCreateDto ticket,
                                                     @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        ticketService.addTicket(ticket.getSlaId(), ticket.getDepartmentId(), ticket.getFloor(),
                ticket.getTitle(), ticket.getDescription(), ticket.getUserId(), author.getUserId(),
                ticket.getHelpdeskId(), ticket.getRoomNumber(), ticket.getGroupId());

        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Ticket created!")
                .build(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateTicket(@RequestBody TicketUpdateDto ticket,
                                                     @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        ticketService.updateTicket(ticket.getTicketId(), ticket.getSlaId(), ticket.getStageId(),
                ticket.getTitle(), ticket.getDescription(), ticket.getHelpdeskId(), ticket.getGroupId(),
                ticket.getRoomNumber(), ticket.getFloor(), author.getUserId());

        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Ticket updated!")
                .build());
    }

    @PostMapping("{ticketId}/close")
    public ResponseEntity<BaseResponse> closeTicket(@PathVariable Integer ticketId,
                                                    @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        ticketService.closeTicket(ticketId, author.getUserId());
        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Ticket updated!")
                .build());
    }
}
