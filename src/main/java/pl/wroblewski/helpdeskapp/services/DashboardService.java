package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wroblewski.helpdeskapp.dto.JobDto;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.repositories.UserApplicationRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;
import pl.wroblewski.helpdeskapp.repositories.UserTicketRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final UserTicketRepository userTicketRepository;
    private final UserApplicationRepository userApplicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<JobDto> getAllJobs(String jobType, Integer jobNumber, Integer userId, Integer slaId,
                                   Integer stageId, Integer userAuthorId)
            throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);

        if (RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }

        List<JobDto> result = new ArrayList<>();
        if (jobType == null || jobType.isEmpty() || jobType.equals("ticket")) {
            result.addAll(userTicketRepository
                    .findByTicketNumberAndUserIdAndSlaIdAndStageId(jobNumber, userId, slaId, stageId)
                    .stream()
                    .map(obj -> modelMapper.map(obj, JobDto.class))
                    .toList());
        }
        if (jobType == null || jobType.isEmpty() || jobType.equals("application")) {
            result.addAll(userApplicationRepository
                    .findByApplicationNumberAndUserIdAndSlaIdAndStageId(jobNumber, userId, slaId, stageId)
                    .stream()
                    .map(obj -> modelMapper.map(obj, JobDto.class))
                    .toList());
        }

        return result;
    }
}
