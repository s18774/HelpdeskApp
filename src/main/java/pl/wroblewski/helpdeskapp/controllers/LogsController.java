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
import pl.wroblewski.helpdeskapp.dto.ExperienceLevelDto;
import pl.wroblewski.helpdeskapp.dto.LogsDto;
import pl.wroblewski.helpdeskapp.services.LogsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class LogsController extends BaseController {
    private final LogsService logsService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<LogsDto>> getAllLogs(@AuthenticationPrincipal UserDetails userDetails) {
        var logs = logsService.getAllLogs()
                .stream()
                .map(exp -> modelMapper.map(exp, LogsDto.class))
                .toList();
        return ResponseEntity.ok(logs);
    }
}
