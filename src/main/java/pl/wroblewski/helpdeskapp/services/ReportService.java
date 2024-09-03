package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.repositories.UserApplicationRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;
import pl.wroblewski.helpdeskapp.utils.PdfUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserApplicationRepository userApplicationRepository;
    private final UserRepository userRepository;
    private final PdfUtil pdfUtil;

    public byte[] getHelpdeskReport(LocalDate dateFrom, LocalDate dateTo, Integer helpdeskId, String jobType,
                                    Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        User helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);

        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Raport</h1>")
                .append("<p>Helpdesk ")
                .append(helpdesk.getFullName())
                .append(" from ")
                .append(dateFrom)
                .append(" to ")
                .append(dateTo)
                .append("</p>");

        sb.append("<table style='width: 100%'><thead><tr>")
                .append("<th>Id</th>")
                .append("<th>Closing date</th>")
                .append("</tr></thead>")
                .append("<tbody>");

        switch (jobType) {
            case "applications" -> {
                List<UserApplication> applications = userApplicationRepository.findAllByResolverUserAndClosingDateBetween(helpdesk, dateFrom, dateTo);
                for(var app : applications) {
                    sb.append("<tr>")
                            .append("<td>").append(app.getId().getApplicationId()).append("</td>")
                            .append("<td>").append(app.getClosingDate()).append("</td>")
                            .append("</tr>");

                }
            }
            case "tickets" -> {}
            case "both" -> {}
        }


        sb.append("</tbody></table>");
        return pdfUtil.toPdf(sb.toString());
    }
}
