package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.models.UserTicketId;

import java.util.List;
import java.util.Optional;

public interface UserTicketRepository extends CrudRepository<UserTicket, UserTicketId> {
    //wyszukiwanie zgłoszeń dla konkretnego użytkownika

    @Query("Select ut FROM UserTicket ut INNER JOIN Ticket t ON t.ticketId=ut.id.ticketId " +
            "WHERE (:ticketId IS NULL OR t.ticketId = :ticketId) " +
            "AND (:userId IS NULL OR ut.id.userId = :userId) " +
            "AND (:stageId IS NULL OR ut.stageId.stageId = :stageId) " +
            "AND (:slaId IS NULL OR t.sla.slaId = :slaId) ")
    List<UserTicket> findByTicketIdAndUserIdAndSlaIdAndStageId(@Param("ticketId") Integer ticketId,
                                                               @Param("userId") Integer userId,
                                                               @Param("slaId") Integer slaId,
                                                               @Param("stageId") Integer stageId);

    @Query("Select ut FROM UserTicket ut INNER JOIN Ticket t ON t.ticketId=ut.id.ticketId " +
            "WHERE ut.id.ticketId = :ticketId")
    Optional<UserTicket> findByTicketId(@Param("ticketId") Integer ticketId);

    List<UserTicket> findAllByResolverUser(User resolverUser);
}
