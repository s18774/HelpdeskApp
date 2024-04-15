package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.dto.JobDto;
import pl.wroblewski.helpdeskapp.repositories.UserApplicationRepository;
import pl.wroblewski.helpdeskapp.repositories.UserTicketRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final UserTicketRepository userTicketRepository;
    private final UserApplicationRepository userApplicationRepository;
    private final ModelMapper modelMapper;

    public List<JobDto> getAllJobs(String jobType, Integer id, Integer userId, Integer slaId) {
        List<JobDto> result = new ArrayList<>();
        if(jobType == null || jobType.equals("ticket")) {
            result.addAll(userTicketRepository
                    .findByTicketIdAndUserIdAndSlaId(id, userId, slaId)
                    .stream()
                    .map(obj -> modelMapper.map(obj, JobDto.class))
                    .toList());
        }
        if(jobType == null || jobType.equals("application")) {
            result.addAll(userApplicationRepository
                    .findByApplicationIdAndUserIdAndSlaId(id, userId, slaId)
                    .stream()
                    .map(obj -> modelMapper.map(obj, JobDto.class))
                    .toList());
        }

        return result;
    }
}
