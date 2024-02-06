package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<List<ApplicationDto>> getApplications(@PathParam("applicationId") Integer applicationId, @PathParam("userId") Integer userId, @PathParam("slaId") Integer slaId) {
        return ResponseEntity.ok(applicationService
                .getAllApplications(applicationId, userId, slaId)
                .stream()
                .map(t -> modelMapper.map(t, ApplicationDto.class))
                .toList());
    }
}
