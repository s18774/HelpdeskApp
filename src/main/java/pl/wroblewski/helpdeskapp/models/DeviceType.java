package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "DeviceType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceType_id", nullable = false)
    private Integer deviceTypeId;

    @Column(name = "type_description", length = 20, nullable = false)
    private String typeDescription;
}
