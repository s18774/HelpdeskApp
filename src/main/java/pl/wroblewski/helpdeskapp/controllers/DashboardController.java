package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.JobDto;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.DashboardService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin("https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io")
@RequiredArgsConstructor
public class DashboardController extends BaseController {
    private final DashboardService dashboardService;
    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<JobDto>> getTickets(@PathParam("jobType") String jobType,
                                                   @PathParam("id") Integer id,
                                                   @PathParam("userId") Integer userId,
                                                   @PathParam("slaId") Integer slaId,
                                                   @PathParam("stageId") Integer stageId,
                                                   @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException {
        User author = userService.getUser(userDetails.getUsername());

        return ResponseEntity.ok(dashboardService.getAllJobs(jobType, id, userId, slaId, stageId, author.getUserId()));
    }
}
