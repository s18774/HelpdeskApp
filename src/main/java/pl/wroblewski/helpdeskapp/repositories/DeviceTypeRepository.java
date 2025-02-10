package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.DeviceType;

import java.util.List;

public interface DeviceTypeRepository extends CrudRepository<DeviceType, Integer> {
    List<DeviceType> findAllByOrderByTypeDescription();
}
