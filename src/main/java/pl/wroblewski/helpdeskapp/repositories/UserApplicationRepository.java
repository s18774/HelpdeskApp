package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.models.UserApplicationId;
import pl.wroblewski.helpdeskapp.models.UserTicket;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserApplicationRepository extends CrudRepository<UserApplication, UserApplicationId> {
    //wyszukujemy wnioski konkretnego użytkownika

    @Query("Select ua FROM UserApplication ua INNER JOIN Application a ON a.applicationId = ua.id.applicationId " +
            "WHERE (:applicationId IS NULL OR a.applicationId = :applicationId) " +
            "AND (:userId IS NULL OR ua.id.userId = :userId) " +
            "AND (:stageId IS NULL OR ua.stageId.stageId = :stageId) " +
            "AND (:slaId IS NULL OR a.sla.slaId = :slaId) ")
    List<UserApplication> findByApplicationIdAndUserIdAndSlaIdAndStageId(@Param("applicationId") Integer applicationId,
                                                                         @Param("userId") Integer userId,
                                                                         @Param("slaId") Integer slaId,
                                                                         @Param("stageId") Integer stageId);

    @Query("Select ua FROM UserApplication ua INNER JOIN Application a ON a.applicationId=ua.id.applicationId " +
            "WHERE ua.id.applicationId = :applicationId")
    Optional<UserApplication> findByApplicationId(@Param("applicationId") Integer applicationId);

    List<UserApplication> findAllByResolverUserAndClosingDateBetweenOrderByClosingDate(User resolverUser, LocalDate dateFrom, LocalDate dateTo); //TODO dodac filtrowanie pod dacie

    List<UserApplication> findAllByClosingDateBetweenOrderByClosingDate(LocalDate dateFrom, LocalDate dateTo);
}
