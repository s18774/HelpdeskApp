package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.UserDevice;

public interface UserDeviceRepository extends CrudRepository<UserDevice, Integer> {
    //możemy wpisać device_id i znaleźć do kogo należy urządzenie lub wpisać user_id i sprawdzić listę wszystkich urządzeń na stanie usera
}
