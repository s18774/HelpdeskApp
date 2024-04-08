package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.repositories.UserApplicationRepository;
import pl.wroblewski.helpdeskapp.repositories.UserTicketRepository;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final UserTicketRepository userTicketRepository;
    private final UserApplicationRepository userApplicationRepository;

//    public List<> getAllJobs() {
//
//    }
}

// return (List<UserTicket>) userTicketRepository.findByTicketIdAndUserIdAndSlaId(ticketId, userId, slaId);
// return userApplicationRepository.findByApplicationIdAndUserIdAndSlaId(applicationId, userId, slaId);