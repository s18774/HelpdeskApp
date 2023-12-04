package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Device;

public interface DeviceRepository extends CrudRepository<Device, Integer> {
}
