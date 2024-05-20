package pl.wroblewski.helpdeskapp.dto;

import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceCreateDto {
    private Integer deviceTypeId;
    private String brand;
    private String model;
    private String serialNumber;
    private Boolean guarantee;
    private String inventoryNumber;
}
