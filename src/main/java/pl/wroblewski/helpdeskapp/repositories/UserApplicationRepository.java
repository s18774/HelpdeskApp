package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.models.UserApplicationId;
import pl.wroblewski.helpdeskapp.models.UserTicket;

import java.util.List;

public interface UserApplicationRepository extends CrudRepository<UserApplication, UserApplicationId> {
    //wyszukujemy wnioski konkretnego użytkownika

    @Query("Select ua FROM UserApplication ua INNER JOIN Application a " +
            "WHERE (:applicationId IS NULL OR a.applicationId = :applicationId) " +
            "AND (:userId IS NULL OR ua.id.userId = :userId) " +
            "AND (:slaId IS NULL OR a.sla.slaId = :slaId) ")
    List<UserTicket> findByApplicationIdAndUserIdAndSlaId(@Param("applicationId") Integer applicationId, @Param("userId") Integer userId, @Param("slaId") Integer slaId);
}
