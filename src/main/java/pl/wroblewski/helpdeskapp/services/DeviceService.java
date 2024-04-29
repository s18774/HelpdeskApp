package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.RoleType;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserDevice;
import pl.wroblewski.helpdeskapp.repositories.UserDeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {
    //to do list
    //pobieranie do listy wszystkich istniejących urządzeń
    //pobieranie listy urządzeń dla konkretnego użytkownika
    //pobieranie urządzenia po konkretnym parametrze(nr. seryjny/inwentarzowy, na gwarancji/po gwarancji)
    private final UserDeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public List<UserDevice> getAllDevices(Integer deviceTypeId, String brand, String model, String serialNumber, Integer userId, Integer userAuthorId) throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return (List<UserDevice>) deviceRepository.findByDeviceTypeIdAndBrandAndModelAndSerialNumberAndUserId(deviceTypeId, brand, model, serialNumber, userId);
    }
}
