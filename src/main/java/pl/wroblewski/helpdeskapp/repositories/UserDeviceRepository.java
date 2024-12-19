package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Device;
import pl.wroblewski.helpdeskapp.models.UserDevice;
import pl.wroblewski.helpdeskapp.models.UserDeviceId;

import java.util.Optional;

public interface UserDeviceRepository extends CrudRepository<UserDevice, UserDeviceId> {
    Optional<UserDevice> findByDevice(Device device);
}
