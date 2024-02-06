package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.repositories.UserApplicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final UserApplicationRepository userApplicationRepository;

    //to do list

    //pobieranie listy wniosków
    //pobieranie wniosków o konkretnym statusie/otwarte zamknięte
    //pobieranie wniosków dla konkretnego użytkownika
    //pobieranie wniosków z konkretnym sla(ważnością wykonania)
    //pobieranie wniosków z danego przedziału czasu od - do
    //
    //tworzenie wniosku(wniosek może stworzyć zalogowany użytkownik, helpdesk, admin)
    //zamykanie wniosku(helpdesk, admin)
    //nadawanie sla do wniosku(helpdesk, admin)
    //przypisanie konkretnego pracownika do wniosku(może to zrobić tylko admin)
    public List<UserApplication> getAllApplications(Integer applicationId, Integer userId, Integer slaId) {
        return userApplicationRepository.findByApplicationIdAndUserIdAndSlaId(applicationId, userId, slaId);
    }

}
