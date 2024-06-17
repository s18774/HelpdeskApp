package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.Stage;
import pl.wroblewski.helpdeskapp.repositories.StageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {
    private final StageRepository stageRepository;

    public List<Stage> getAllStages() {
        return (List<Stage>) stageRepository.findAll();
    }
}
