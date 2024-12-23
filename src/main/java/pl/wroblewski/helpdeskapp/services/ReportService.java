package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.PdfException;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.repositories.UserApplicationRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;
import pl.wroblewski.helpdeskapp.repositories.UserTicketRepository;
import pl.wroblewski.helpdeskapp.utils.PdfUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserApplicationRepository userApplicationRepository;
    private final UserTicketRepository userTicketRepository;
    private final UserRepository userRepository;
    private final PdfUtil pdfUtil;
    private final LogsService logsService;

    public byte[] getHelpdeskReport(LocalDate dateFrom, LocalDate dateTo, Integer helpdeskId, String jobType,
                                    Integer userAuthorId) throws UserNotExistsException, PermissionsException, PdfException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        User helpdesk = null;
        if (helpdeskId != null) {
            helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Report</h1>")
                .append("<p>Helpdesk ")
                .append(helpdesk != null ? helpdesk.getFullName() : "all employees")
                .append(" from ")
                .append(dateFrom)
                .append(" to ")
                .append(dateTo)
                .append("</p>");

        sb.append("<table style='width: 100%'><thead><tr>")
                .append("<th>Id</th>")
                .append("<th>Closing date</th>")
                .append("<th>Type</th>");

        if (helpdesk == null) {
            sb.append("<th>Closed by</th>");
        }

        sb.append("</tr></thead>")
                .append("<tbody>");

        if (jobType.equals("application") || jobType.equals("all")) {
            List<UserApplication> applications;
            if (helpdesk != null) {
                applications = userApplicationRepository
                        .findAllByResolverUserAndClosingDateBetweenOrderByClosingDate(helpdesk, dateFrom, dateTo);
            } else {
                applications = userApplicationRepository
                        .findAllByClosingDateBetweenOrderByClosingDate(dateFrom, dateTo);
            }

            for (var app : applications) {
                sb.append("<tr>")
                        .append("<td>").append(app.getId().getApplicationId()).append("</td>")
                        .append("<td>").append(app.getClosingDate()).append("</td>")
                        .append("<td>application</td>");
                if (helpdesk == null) {
                    sb.append("<td>").append(app.getResolverUser().getFullName()).append("</td>");
                }
                sb.append("</tr>");

            }
        }
        if (jobType.equals("ticket") || jobType.equals("all")) {
            List<UserTicket> tickets;
            if (helpdesk != null) {
                tickets = userTicketRepository
                        .findAllByResolverUserAndClosingDateBetweenOrderByClosingDate(helpdesk, dateFrom, dateTo);
            } else {
                tickets = userTicketRepository.findAllByClosingDateBetweenOrderByClosingDate(dateFrom, dateTo);
            }
            for (var ticket : tickets) {
                sb.append("<tr>")
                        .append("<td>").append(ticket.getId().getTicketId()).append("</td>")
                        .append("<td>").append(ticket.getClosingDate()).append("</td>")
                        .append("<td>ticket</td>");
                if (helpdesk == null) {
                    sb.append("<td>").append(ticket.getResolverUser().getFullName()).append("</td>");
                }
                sb.append("</tr>");

            }
        }

        sb.append("</tbody></table>");
        byte[] reportBytes = pdfUtil.toPdf(sb.toString());

        logsService.log(String.format("%s (%d) created report", userAuthor.getFullName(), userAuthor.getUserId()));

        return reportBytes;
    }
}
