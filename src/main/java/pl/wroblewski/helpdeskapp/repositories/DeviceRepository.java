package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Device;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
    //wyszukiwanie urządzenia po numerze seryjnym lub inwentarzowym aby następnie mając device id sprawdzić do jakiego usera należy
}
