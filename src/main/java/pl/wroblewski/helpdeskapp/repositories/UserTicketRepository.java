package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.UserTicket;

public interface UserTicketRepository extends CrudRepository<UserTicket, Integer> {
    //wyszukiwanie zgłoszeń dla konkretnego użytkownika

}
