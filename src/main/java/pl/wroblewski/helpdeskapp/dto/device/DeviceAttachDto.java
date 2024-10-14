package pl.wroblewski.helpdeskapp.dto.device;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class DeviceAttachDto {
    private Integer userId;
    private Integer deviceId;
}
