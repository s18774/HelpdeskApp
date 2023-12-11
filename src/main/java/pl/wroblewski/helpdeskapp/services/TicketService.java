package pl.wroblewski.helpdeskapp.services;

import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.Ticket;
import pl.wroblewski.helpdeskapp.repositories.TicketRepository;

import java.util.List;

@Service
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
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
