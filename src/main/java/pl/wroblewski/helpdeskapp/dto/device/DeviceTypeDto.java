package pl.wroblewski.helpdeskapp.dto.device;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DeviceTypeDto {
    private Integer deviceTypeId;
    private String typeDescription;
}
