package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.DeviceType;

public interface DeviceTypeRepository extends CrudRepository<DeviceType, Integer> {
}
