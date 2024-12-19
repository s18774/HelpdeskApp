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
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.ReportService;
import pl.wroblewski.helpdeskapp.services.UserService;
import pl.wroblewski.helpdeskapp.utils.PdfUtil;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class ReportController extends BaseController {
    private final ReportService reportService;
    private final UserService userService;
    private final PdfUtil pdfUtil;

    @GetMapping("helpdesk")
    public ResponseEntity<byte[]> getHelpdeskReport(@PathParam("dateFrom") LocalDate dateFrom,
                                                    @PathParam("dateTo") LocalDate dateTo,
                                                    @PathParam("helpdeskId") Integer helpdeskId,
                                                    @PathParam("jobType") String jobType,
                                                    @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        byte[] contents = reportService.getHelpdeskReport(dateFrom, dateTo, helpdeskId, jobType, author.getUserId());
        return pdfUtil.createPdfFileResponse(contents, "report.pdf");
    }
}
