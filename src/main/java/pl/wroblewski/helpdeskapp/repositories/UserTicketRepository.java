package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.models.UserTicketId;

public interface UserTicketRepository extends CrudRepository<UserTicket, UserTicketId> {
    //wyszukiwanie zgłoszeń dla konkretnego użytkownika

}
