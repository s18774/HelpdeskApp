package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "SLA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SLA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sla_id")
    private Integer slaId;

    @Column(name = "sla_level")
    private Short slaLevel;
}
