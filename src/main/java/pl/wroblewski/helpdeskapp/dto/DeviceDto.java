package pl.wroblewski.helpdeskapp.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceDto {
    private String deviceTypeName;
    private String brand;
    private String model;
    private String serialNumber;
    private String fullName;
}
