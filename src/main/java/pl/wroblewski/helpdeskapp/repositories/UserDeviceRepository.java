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

//    @Query("Select ud FROM UserDevice ud INNER JOIN Device d ON d.deviceId=ud.id.deviceId " +
//            "WHERE (:deviceTypeId IS NULL OR d.deviceType.deviceTypeId = :deviceTypeId) " +
//            "AND (:brand IS NULL OR d.brand = :brand) " +
//            "AND (:model IS NULL OR d.model = :model) " +
//            "AND (:serialNumber IS NULL OR d.model = :serialNumber) " +
//            "AND (:userId IS NULL OR ud.id.deviceId = :userId) ")
//    public List<UserDevice> findByDeviceTypeIdAndBrandAndModelAndSerialNumberAndUserId(@Param("deviceTypeId") Integer deviceTypeId,
//                                                                                       @Param("brand") String brand,
//                                                                                       @Param("model") String model,
//                                                                                       @Param("serialNumber") String serialNumber,
//                                                                                       @Param("userId") Integer userId);
}
