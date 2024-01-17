package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.ApplicationDto;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.services.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/application")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAllTickets() {
        return ResponseEntity.ok(applicationService
                .getAllTickets()
                .stream()
                .map(t -> modelMapper.map(t, ApplicationDto.class))
                .toList());
    }
}
