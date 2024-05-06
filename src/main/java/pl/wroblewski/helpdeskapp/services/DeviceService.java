package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.DeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.DeviceTypeRepository;
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
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final DeviceTypeRepository deviceTypeRepository;

    public List<Device> getAllDevices(Integer deviceTypeId, String brand, String model, String serialNumber, Integer userId, Integer userAuthorId) throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return (List<Device>) deviceRepository.findByDeviceTypeIdAndBrandAndModelAndSerialNumberAndUserId(deviceTypeId, brand, model, serialNumber, userId);
    }

    public List<DeviceType> getAllTypes() {
        return (List<DeviceType>) deviceTypeRepository.findAll();
    }
}
