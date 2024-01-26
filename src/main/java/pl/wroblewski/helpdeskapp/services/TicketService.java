package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.repositories.TicketRepository;
import pl.wroblewski.helpdeskapp.repositories.UserTicketRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    //to do list

    //pobieranie listy zgłoszeń
    //pobieranie zgłoszeń o konkretnym statusie/otwarte zamknięte
    //pobieranie zgłoszeń dla konkretnego użytkownika(użytkownik po zalogowaniu może zobaczyć listę swoich zgłoszeń otwartych/zamkniętych)
    //pobieranie zgłoszeń z konkretnym sla(ważnością wykonania)
    //pobieranie zgłoszeń z danego przedziału czasu od - do
    //
    //tworzenie zgłoszenia(zgłoszenie może stworzyć zalogowany użytkownik, helpdesk, admin)
    //zamykanie zgłoszenia(helpdesk, admin)
    //nadawanie sla do zgłoszenia(helpdesk, admin)
    //przypisanie konkretnego pracownika do zgłoszenia(może to zrobić tylko admin)
    private final TicketRepository ticketRepository;
    private final UserTicketRepository userTicketRepository;


    public List<UserTicket> getTickets(Integer ticketId, Integer userId, Integer slaId) {
        return (List<UserTicket>) userTicketRepository.findByTicketIdAndUserIdAndSlaId(ticketId, userId, slaId);
    }

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
