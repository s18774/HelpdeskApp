package pl.wroblewski.helpdeskapp.dto.device;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceUpdateDto {
    private Integer deviceId;
    private Integer userId;
    private String inventoryNumber;
    private Byte isGuarantee;
    private String ipAddress;
}
