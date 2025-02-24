package pl.wroblewski.helpdeskapp.dto.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceCreateDto {
    @NotNull
    private Integer deviceTypeId;
    @NotBlank
    private String brand;
    private String model;
    @NotBlank
    private String serialNumber;
    private boolean guarantee;
    @NotBlank
    private String inventoryNumber;
    private String macAddress;
}
