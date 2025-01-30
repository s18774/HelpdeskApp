package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.LogsDto;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.LogsService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@CrossOrigin("https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io")
@RequiredArgsConstructor
public class LogsController extends BaseController {
    private final LogsService logsService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<LogsDto>> getAllLogs(@AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        var logs = logsService.getAllLogs(author.getUserId())
                .stream()
                .map(exp -> modelMapper.map(exp, LogsDto.class))
                .toList();
        return ResponseEntity.ok(logs);
    }
}
