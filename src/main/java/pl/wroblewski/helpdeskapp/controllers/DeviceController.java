package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.device.*;
import pl.wroblewski.helpdeskapp.exceptions.DeviceAlreadyAttachedException;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserDevice;
import pl.wroblewski.helpdeskapp.services.DeviceService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/device")
@CrossOrigin( origins = {"https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io",
        "http://localhost:3000"})
@RequiredArgsConstructor
public class DeviceController extends BaseController {
    private final DeviceService deviceService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<DeviceDto>> getDevices(@PathParam("deviceTypeId") Integer deviceTypeId,
                                                      @PathParam("brand") String brand,
                                                      @PathParam("model") String model,
                                                      @PathParam("serialNumber") String serialNumber,
                                                      @PathParam("userId") Integer userId,
                                                      @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException {
        User author = userService.getUser(userDetails.getUsername());

        var devices = deviceService
                .getAllDevices(deviceTypeId, brand, model, serialNumber, userId, author.getUserId());

        return ResponseEntity.ok(DeviceDto.toDto(devices, modelMapper));
    }

    @GetMapping("/not-attached")
    public ResponseEntity<List<DeviceDto>> getDevices(@AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        var devices = deviceService.getAllNotAttachedDevices(author.getUserId());
        return ResponseEntity.ok(DeviceDto.toDto(devices, modelMapper));
    }

    @GetMapping("/types")
    public ResponseEntity<List<DeviceTypeDto>> getAllTypes() {
        return ResponseEntity.ok(deviceService.getAllTypes()
                .stream()
                .map(t -> modelMapper.map(t, DeviceTypeDto.class))
                .toList());
    }

    @PostMapping
    public ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceCreateDto deviceCreateDto,
                                                  @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        var newDevice = deviceService.createDevice(deviceCreateDto.getDeviceTypeId(), deviceCreateDto.getBrand(),
                deviceCreateDto.getModel(), deviceCreateDto.getSerialNumber(), deviceCreateDto.getInventoryNumber(),
                deviceCreateDto.getGuarantee(), deviceCreateDto.getMacAddress(), author.getUserId());
        return ResponseEntity.ok(modelMapper.map(newDevice, DeviceDto.class));
    }

    @GetMapping("{deviceId}")
    public ResponseEntity<DeviceDto> getDevice(@PathVariable("deviceId") Integer deviceId,
                                               @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        var device = deviceService.getDevice(deviceId, author.getUserId());
        var deviceDto = modelMapper.map(device, DeviceDto.class);

        if (device.getUserDevices() != null) {
            Optional<User> user = device.getUserDevices().stream().map(UserDevice::getUser).findFirst();
            if (user.isPresent()) {
                deviceDto.setFullName(user.get().getFullName());
                deviceDto.setUserId(user.get().getUserId());
            }
        }

        return ResponseEntity.ok(deviceDto);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateDevice(@RequestBody DeviceUpdateDto device,
                                                     @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        deviceService.updateDevice(device.getDeviceId(), device.getUserId(), device.getInventoryNumber(),
                device.getIsGuarantee(), device.getIpAddress(), author.getUserId());

        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Device updated!")
                .build());
    }

    @PostMapping("/attach")
    public ResponseEntity<BaseResponse> attachDevice(@RequestBody DeviceAttachDto deviceAttach,
                                                     @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, DeviceAlreadyAttachedException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        deviceService.attachDevice(deviceAttach.getUserId(), deviceAttach.getDeviceId(), author.getUserId());
        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Device attached!")
                .build(), HttpStatus.OK);
    }
}
