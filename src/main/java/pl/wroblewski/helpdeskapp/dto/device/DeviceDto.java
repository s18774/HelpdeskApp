package pl.wroblewski.helpdeskapp.dto.device;

import lombok.*;
import org.modelmapper.ModelMapper;
import pl.wroblewski.helpdeskapp.models.Device;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceDto {
    private Integer deviceId;
    private Integer deviceTypeId;
    private String deviceTypeName;
    private String brand;
    private String model;
    private String serialNumber;
    private String fullName;
    private Integer userId;
    private LocalDateTime dateOfPurchase;
    private String inventoryNumber;
    private Byte isGuarantee;
    private String ipAddress;
    private String macAddress;

    public static List<DeviceDto> toDto(List<Device> devices, ModelMapper modelMapper) {
        var devicesDto = devices
                .stream()
                .map(d -> modelMapper.map(d, DeviceDto.class))
                .toList();

        for (var dto : devicesDto) {
            var dev = devices.stream()
                    .filter(x -> x.getSerialNumber().equals(dto.getSerialNumber()) && x.getUserDevices() != null)
                    .findFirst();
            if (dev.isPresent()) {
                var userDevices = dev.get().getUserDevices();
                if (userDevices != null && !userDevices.isEmpty()) {
                    var user = userDevices.stream().findAny().get().getUser();
                    dto.setFullName(user.getFullName());
                    dto.setUserId(user.getUserId());
                }
            }
        }

        return devicesDto;
    }
}
