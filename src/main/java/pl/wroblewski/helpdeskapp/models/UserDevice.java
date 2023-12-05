package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "UserDevice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDevice {
    @EmbeddedId
    private UserDeviceId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("deviceId")
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "location_of_device")
    private String locationOfDevice;

    @Column(name = "ip_addres", nullable = true)
    private String ipAddres;

    @Column(name = "mac_addres", nullable = true)
    private String macAddres;
}
