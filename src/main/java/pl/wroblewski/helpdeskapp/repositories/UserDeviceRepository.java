package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.UserDevice;
import pl.wroblewski.helpdeskapp.models.UserDeviceId;
import pl.wroblewski.helpdeskapp.models.UserTicket;

import java.util.List;

public interface UserDeviceRepository extends CrudRepository<UserDevice, UserDeviceId> {
    //możemy wpisać device_id i znaleźć do kogo należy urządzenie lub wpisać user_id i sprawdzić listę wszystkich urządzeń na stanie usera

    @Query("Select ut FROM UserTicket ut INNER JOIN Ticket t ON t.ticketId=ut.id.ticketId " +
            "WHERE (:ticketId IS NULL OR t.ticketId = :ticketId) " +
            "AND (:userId IS NULL OR ut.id.userId = :userId) " +
            "AND (:slaId IS NULL OR t.sla.slaId = :slaId) ") //TODO: przeobic zapytanie pod te parametry na dole
    public List<UserDevice> findByDeviceTypeIdAndBrandAndModelAndSerialNumberAndUserId(@Param("deviceTypeId") Integer deviceTypeId,
                                                                                       @Param("brand") String brand,
                                                                                       @Param("model") String model,
                                                                                       @Param("serialNumber") String serialNumber,
                                                                                       @Param("userId") Integer userId);
}
