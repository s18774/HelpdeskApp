package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.wroblewski.helpdeskapp.exceptions.DeviceAlreadyAttachedException;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.*;
import pl.wroblewski.helpdeskapp.repositories.DeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.DeviceTypeRepository;
import pl.wroblewski.helpdeskapp.repositories.UserDeviceRepository;
import pl.wroblewski.helpdeskapp.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final DeviceTypeRepository deviceTypeRepository;
    private final UserDeviceRepository userDeviceRepository;
    private final LogsService logsService;

    public List<Device> getAllDevices(Integer deviceTypeId, String brand, String model, String serialNumber,
                                      Integer userId, Integer userAuthorId)
            throws UserNotExistsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (RoleType.isUser(userAuthor)) {
            userId = userAuthorId;
        }
        return deviceRepository.findByDeviceTypeIdAndBrandAndModelAndSerialNumberAndUserId(deviceTypeId,
                brand, model, serialNumber, userId);
    }

    public List<DeviceType> getAllTypes() {
        return (List<DeviceType>) deviceTypeRepository.findAll();
    }

    @Transactional
    public Device createDevice(Integer deviceTypeId, String brand, String model, String serialNumber,
                               String inventoryNumber, Boolean isGuarantee, String macAddress, Integer userAuthorId)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        DeviceType deviceType = deviceTypeRepository.findById(deviceTypeId)
                .orElseThrow(() -> new EntityNotExists(DeviceType.class));

        Device device = deviceRepository.save(Device.builder()
                .deviceType(deviceType)
                .brand(brand)
                .serialNumber(serialNumber)
                .model(model)
                .macAddress(macAddress)
                .dateOfPurchase(LocalDateTime.now())
                .isGuarantee((byte) (isGuarantee != null && isGuarantee ? 1 : 0))
                .inventoryNumber(inventoryNumber)
                .build());

        logsService.log(String.format("%s (%d) created device %s (%s)",
                userAuthor.getFullName(),
                userAuthorId,
                device.getModel() + " " + device.getBrand(),
                device.getSerialNumber()));

        return device;
    }

    public Device getDevice(Integer deviceId, Integer userAuthorId)
            throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotExists(Device.class));
        if (!RoleType.isHelpdesk(userAuthor) && !RoleType.isAdmin(userAuthor)
                && !userHasDevice(userAuthor, device)) {
            throw new PermissionsException();
        }
        return device;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateDevice(Integer deviceId, Integer userId, String inventoryNumber, Byte isGuarantee, String ipAddress, Integer userAuthorId) throws UserNotExistsException, EntityNotExists, PermissionsException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotExists(Device.class));

        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        if (userId != null) {
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

        logsService.log(String.format("%s (%d) updated device %s (%s)",
                userAuthor.getFullName(),
                userAuthorId,
                device.getModel() + " " + device.getBrand(),
                device.getSerialNumber()));
    }

    public List<Device> getAllNotAttachedDevices(Integer userAuthorId)
            throws UserNotExistsException, PermissionsException {
        User user = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(user)) {
            throw new PermissionsException();
        }
        return deviceRepository.getAllNotAttached();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void attachDevice(Integer userId, Integer deviceId, Integer userAuthorId)
            throws UserNotExistsException, EntityNotExists, PermissionsException, DeviceAlreadyAttachedException {
        User userAuthor = userRepository.findById(userAuthorId).orElseThrow(UserNotExistsException::new);
        if (!RoleType.isAdmin(userAuthor)) {
            throw new PermissionsException();
        }

        User user = userRepository.findById(userId).orElseThrow(UserNotExistsException::new);
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotExists(Device.class));

        Optional<UserDevice> userDevice = userDeviceRepository.findByDevice(device);
        if (userDevice.isPresent()) {
            throw new DeviceAlreadyAttachedException();
        }
        UserDevice newUserDevice = UserDevice.builder()
                .id(UserDeviceId.builder().userId(userId).deviceId(deviceId).build())
                .device(device)
                .user(user)
                .locationOfDevice(user.getRoom() + "")
                .build();
        userDeviceRepository.save(newUserDevice);

        logsService.log(String.format("%s (%d) attached device %s (%s) to user %s (%d)",
                userAuthor.getFullName(),
                userAuthorId,
                device.getModel() + " " + device.getBrand(),
                device.getSerialNumber(),
                user.getFullName(),
                user.getUserId()));
    }

    private boolean userHasDevice(User user, Device device) {
        return device.getUserDevices().stream().anyMatch(d -> d.getUser().getUserId() == user.getUserId());
    }
}
