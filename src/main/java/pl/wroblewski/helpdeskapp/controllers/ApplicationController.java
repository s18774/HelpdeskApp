package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.*;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.ApplicationService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/application")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ApplicationController extends BaseController {
    private final ApplicationService applicationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplications(@PathParam("applicationId") Integer applicationId, @PathParam("userId") Integer userId, @PathParam("slaId") Integer slaId, @AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException {
        User author = userService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(applicationService
                .getAllApplications(applicationId, userId, slaId, author.getUserId())
                .stream()
                .map(t -> modelMapper.map(t, ApplicationDto.class))
                .toList());
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createApplication(@RequestBody ApplicationCreateDto applicationDto, @AuthenticationPrincipal UserDetails userDetails) throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        applicationService.addApplication(applicationDto.getSlaId(), applicationDto.getTitle(),
                applicationDto.getDescription(), applicationDto.getUserId(), author.getUserId(),
                applicationDto.getHelpdeskId(), applicationDto.getGroupId());

        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Application created!")
                .build(), HttpStatus.CREATED);
    }
}
