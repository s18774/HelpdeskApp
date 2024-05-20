package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.DeviceCreateDto;
import pl.wroblewski.helpdeskapp.dto.DeviceDto;
import pl.wroblewski.helpdeskapp.dto.DeviceTypeDto;
import pl.wroblewski.helpdeskapp.dto.TicketDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.DeviceService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device")
@CrossOrigin("http://localhost:3000")
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
                                                      @AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        var devices = deviceService
                .getAllDevices(deviceTypeId, brand, model, serialNumber, userId, author.getUserId());

        var devicesDto = devices
                .stream()
                .map(d -> modelMapper.map(d, DeviceDto.class))
                .toList();

        devicesDto.forEach(dto -> dto.setFullName(devices.stream()
                .filter(x -> x.getSerialNumber().equals(dto.getSerialNumber()) && x.getUserDevices() != null)
                .findFirst().flatMap(first -> first.getUserDevices().stream().findFirst().map(x -> x.getUser().getFullName())).orElse(null)));


        return ResponseEntity.ok(devicesDto);
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
                deviceCreateDto.getGuarantee(), author.getUserId());
        return ResponseEntity.ok(modelMapper.map(newDevice, DeviceDto.class));
    }
}
