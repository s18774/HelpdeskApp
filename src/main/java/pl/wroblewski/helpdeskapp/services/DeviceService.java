package pl.wroblewski.helpdeskapp.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.exceptions.DeviceAlreadyAttachedException;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.DeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.DeviceTypeRepository;
import pl.wroblewski.helpdeskapp.repositories.UserDeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    private final UserDeviceRepository userDeviceRepository;

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

    public Device createDevice(Integer deviceTypeId, String brand, String model, String serialNumber, String inventoryNumber, Boolean isGuarantee, String macAddress,  Integer userAuthorId)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        DeviceType deviceType = deviceTypeRepository.findById(deviceTypeId).orElseThrow(() -> new EntityNotExists(DeviceType.class));

        return deviceRepository.save(Device.builder()
                .deviceType(deviceType)
                .brand(brand)
                .serialNumber(serialNumber)
                .model(model)
                .macAddress(macAddress)
                .dateOfPurchase(LocalDateTime.now())
                .isGuarantee((byte)(isGuarantee != null && isGuarantee ? 1 : 0))
                .inventoryNumber(inventoryNumber)
                .build());
    }

    public Device getDevice(Integer deviceId, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotExists(Device.class));
        if(!RoleType.isHelpdesk(userAuthor) && !RoleType.isAdmin(userAuthor)
                && !userHasDevice(userAuthor, device)) {
            throw new PermissionsException();
        }
        return device;
    }

    @Transactional
    public void updateDevice(Integer deviceId, Integer userId, String inventoryNumber, Byte isGuarantee, String ipAddress, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotExists(Device.class));

        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        if(userId != null) {
            User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);

            Optional<UserDevice> userDevice = userDeviceRepository.findByDevice(device);
            userDevice.ifPresent(userDeviceRepository::delete);
            UserDevice newUserDevice = UserDevice.builder()
                    .id(UserDeviceId.builder().userId(userId).deviceId(deviceId).build())
                    .device(device)
                    .user(user)
                    .locationOfDevice(user.getRoom() + "")
                    .build();
            userDeviceRepository.save(newUserDevice);
        } else {
            Optional<UserDevice> userDevice = userDeviceRepository.findByDevice(device);
            userDevice.ifPresent(userDeviceRepository::delete);
        }

        device.setInventoryNumber(inventoryNumber);
        device.setIsGuarantee(isGuarantee);
        device.setIpAddress(ipAddress);
        deviceRepository.save(device);
    }

    public List<Device> getAllNotAttachedDevices() {
        return deviceRepository.getAllNotAttached();
    }

    public void attachDevice(Integer userId, Integer deviceId, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException, DeviceAlreadyAttachedException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if(!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotExists(Device.class));

        Optional<UserDevice> userDevice = userDeviceRepository.findByDevice(device);
        if(userDevice.isPresent()) {
            throw new DeviceAlreadyAttachedException();
        }
        UserDevice newUserDevice = UserDevice.builder()
                .id(UserDeviceId.builder().userId(userId).deviceId(deviceId).build())
                .device(device)
                .user(user)
                .locationOfDevice(user.getRoom() + "")
                .build();
        userDeviceRepository.save(newUserDevice);
    }

    private boolean userHasDevice(User user, Device device) {
        return device.getUserDevices().stream().anyMatch(d -> d.getUser().getUserId() == user.getUserId());
    }
}
