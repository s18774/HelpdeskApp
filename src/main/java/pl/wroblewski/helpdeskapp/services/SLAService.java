package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.SLA;
import pl.wroblewski.helpdeskapp.repositories.SLARepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SLAService {
    private final SLARepository slaRepository;

    public List<SLA> getAllSLA() {
        return slaRepository.findAll();
    }
}
