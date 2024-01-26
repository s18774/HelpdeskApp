package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.models.UserTicketId;

import java.util.List;

public interface UserTicketRepository extends CrudRepository<UserTicket, UserTicketId> {
    //wyszukiwanie zgłoszeń dla konkretnego użytkownika

    @Query("Select ut FROM UserTicket ut INNER JOIN Ticket t ON t.ticketId=ut.id.ticketId " +
            "WHERE (:ticketId IS NULL OR t.ticketId = :ticketId) " +
            "AND (:userId IS NULL OR ut.id.userId = :userId) " +
            "AND (:slaId IS NULL OR t.sla.slaId = :slaId) ")
    public List<UserTicket> findByTicketIdAndUserIdAndSlaId(@Param("ticketId") Integer ticketId, @Param("userId") Integer userId, @Param("slaId") Integer slaId);
}
