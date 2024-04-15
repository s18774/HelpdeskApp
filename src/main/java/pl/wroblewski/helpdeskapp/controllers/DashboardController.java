package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.JobDto;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.services.DashboardService;
import pl.wroblewski.helpdeskapp.services.TicketService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class DashboardController extends BaseController {
    private final DashboardService dashboardService;


    @GetMapping
    public ResponseEntity<List<JobDto>> getTickets(@PathParam("jobType") String jobType, @PathParam("id") Integer id, @PathParam("userId") Integer userId, @PathParam("slaId") Integer slaId) {
        return ResponseEntity.ok(dashboardService.getAllJobs(jobType, id, userId, slaId));
    }
}
