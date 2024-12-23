package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.Logs;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.repositories.LogsRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsService {
    private final LogsRepository logsRepository;
    private final UserRepository userRepository;

    public void log(String description) {
        logsRepository.save(Logs.builder()
                .date(LocalDateTime.now())
                .description(description)
                .build());
    }

    public List<Logs> getAllLogs(Integer userAuthorId) throws UserNotExistsException, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }
        return (List<Logs>) logsRepository.findAll();
    }
}
