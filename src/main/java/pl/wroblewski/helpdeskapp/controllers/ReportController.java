package pl.wroblewski.helpdeskapp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.services.ReportService;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;


}
