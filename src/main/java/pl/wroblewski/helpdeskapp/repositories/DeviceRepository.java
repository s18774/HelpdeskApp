package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.wroblewski.helpdeskapp.models.Device;
import pl.wroblewski.helpdeskapp.models.UserDevice;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
    //wyszukiwanie urządzenia po numerze seryjnym lub inwentarzowym aby następnie mając device id sprawdzić do jakiego usera należy
    @Query("Select d FROM Device d LEFT JOIN UserDevice ud ON d.deviceId=ud.id.deviceId " +
            "WHERE (:deviceTypeId IS NULL OR d.deviceType.deviceTypeId = :deviceTypeId) " +
            "AND (:brand IS NULL OR d.brand LIKE %:brand%) " +
            "AND (:model IS NULL OR d.model LIKE %:model%) " +
            "AND (:serialNumber IS NULL OR d.model LIKE %:serialNumber%) " +
            "AND (:userId IS NULL OR ud.id.deviceId = :userId) ")
    public List<Device> findByDeviceTypeIdAndBrandAndModelAndSerialNumberAndUserId(@Param("deviceTypeId") Integer deviceTypeId,
                                                                                       @Param("brand") String brand,
                                                                                       @Param("model") String model,
                                                                                       @Param("serialNumber") String serialNumber,
                                                                                       @Param("userId") Integer userId);

    @Query("SELECT d from Device d " +
            "LEFT JOIN UserDevice ud ON d.deviceId =ud.id.deviceId " +
            "WHERE ud.user IS NULL")
    public List<Device> getAllNotAttached();
}
