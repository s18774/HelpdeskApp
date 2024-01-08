package pl.wroblewski.helpdeskapp.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.services.LogsService;

@RestController
@RequestMapping("/api/v1/logs")
@CrossOrigin("http://localhost:3000")
public class LogsController {

    private final LogsService logsService;

    public LogsController(LogsService logsService) {
        this.logsService = logsService;
    }
}
