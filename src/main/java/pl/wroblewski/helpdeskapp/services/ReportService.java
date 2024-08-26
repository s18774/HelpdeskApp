package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;
import pl.wroblewski.helpdeskapp.utils.PdfUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final PdfUtil pdfUtil;

    public byte[] getHelpdeskReport(LocalDate dateFrom, LocalDate dateTo, Integer helpdeskId, Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        User helpdesk = userRepository.findById(helpdeskId).orElseThrow(UserNotExistsException::new);
//        List<UserApplication> applications =

        //TODO: dokonczyc
        return pdfUtil.toPdf("DOKONCZYC");
    }
}
