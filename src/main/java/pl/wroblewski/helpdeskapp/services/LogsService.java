package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.Logs;
import pl.wroblewski.helpdeskapp.repositories.LogsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogsService {
    private final LogsRepository logsRepository;

    public void log(String description) {
        logsRepository.save(Logs.builder()
                .date(LocalDateTime.now())
                .description(description)
                .build());
    }

    public List<Logs> getAllLogs() {
        return (List<Logs>) logsRepository.findAll();
    }
}
