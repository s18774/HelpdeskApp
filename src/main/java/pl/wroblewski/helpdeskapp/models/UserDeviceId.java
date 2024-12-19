package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDeviceId implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "device_id")
    private Integer deviceId;
}
