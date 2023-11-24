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

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device deviceId;

    @Column(name = "location_of_device")
    private String locationOfDevice;

    @Column(name = "ip_addres", nullable = true)
    private String ipAddres;

    @Column(name = "mac_addres", nullable = true)
    private String macAddres;
}
