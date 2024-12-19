package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
}
