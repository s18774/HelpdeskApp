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
import pl.wroblewski.helpdeskapp.dto.application.ApplicationCreateDto;
import pl.wroblewski.helpdeskapp.dto.application.ApplicationDto;
import pl.wroblewski.helpdeskapp.dto.application.ApplicationUpdateDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.services.ApplicationService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/application")
@CrossOrigin( origins = {"https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io",
        "http://localhost:3000"})
@RequiredArgsConstructor
public class ApplicationController extends BaseController {
    private final ApplicationService applicationService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplications(@PathParam("applicationNumber") Integer applicationNumber,
                                                                @PathParam("userId") Integer userId,
                                                                @PathParam("slaId") Integer slaId,
                                                                @PathParam("stageId") Integer stageId,
                                                                @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException {
        User author = userService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(applicationService
                .getAllApplications(applicationNumber, userId, slaId, stageId, author.getUserId())
                .stream()
                .map(t -> modelMapper.map(t, ApplicationDto.class))
                .toList());
    }

    @GetMapping("{applicationId}")
    public ResponseEntity<ApplicationDto> getApplications(@PathVariable Integer applicationId,
                                                     @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        UserApplication ticket = applicationService.getApplication(applicationId, author.getUserId());
        return ResponseEntity.ok(modelMapper.map(ticket, ApplicationDto.class));
    }

    @PostMapping
    public ResponseEntity<BaseResponse> createApplication(@RequestBody @Valid ApplicationCreateDto applicationDto,
                                                          @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        applicationService.addApplication(applicationDto.getSlaId(), applicationDto.getTitle(),
                applicationDto.getDescription(), applicationDto.getUserId(), author.getUserId(),
                applicationDto.getHelpdeskId(), applicationDto.getGroupId(), applicationDto.getTypeOfApplication());

        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Application created!")
                .build(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateApplication(@RequestBody ApplicationUpdateDto application,
                                                          @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        applicationService.updateApplication(application.getApplicationId(), application.getSlaId(),
                application.getStageId(), application.getSubject(), application.getDescription(),
                application.getGroupId(), application.getHelpdeskId(), author.getUserId());

        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Application updated!")
                .build(), HttpStatus.CREATED);
    }

    @PostMapping("{applicationId}/close")
    public ResponseEntity<BaseResponse> closeApplication(@PathVariable Integer applicationId,
                                                         @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        applicationService.closeApplication(applicationId, author.getUserId());
        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Application updated!")
                .build(), HttpStatus.OK);
    }
}
