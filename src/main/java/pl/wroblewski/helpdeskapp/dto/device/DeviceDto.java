package pl.wroblewski.helpdeskapp.dto.device;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
