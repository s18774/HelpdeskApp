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
    //pobieranie zgłoszeń dla konkretnego użytkownika
    //pobieranie zgłoszeń z konkretnym sla(ważnością wykonania)
    //pobieranie zgłoszeń z danego przedziału czasu od - do
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
