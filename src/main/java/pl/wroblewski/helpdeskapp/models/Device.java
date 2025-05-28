package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;
import pl.wroblewski.helpdeskapp.converters.LocalDateTimeStringConverter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "Device")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    private Integer deviceId;

    @ManyToOne
    @JoinColumn(name = "deviceType_id", nullable = false)
    private DeviceType deviceType;

    @Column(name = "brand", length = 20, nullable = false)
    private String brand;

    @Column(name = "model", length = 20)
    private String model;

    @Column(name = "serial_number", length = 30, nullable = false)
    private String serialNumber;

    @Column(name = "inventory_number", length = 30, nullable = false)
    private String inventoryNumber;

    @Column(name = "date_of_purchase", nullable = false)
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime dateOfPurchase;

    @Column(name = "is_guarantee", nullable = false)
    private Byte isGuarantee;//1 means yes, 0 means no

    @Column(name = "ip_addres", nullable = true)
    private String ipAddress;

    @Column(name = "mac_addres", nullable = true)
    private String macAddress;

    @OneToMany
    @JoinColumn(name = "device_id")
    private Set<UserDevice> userDevices;
}
