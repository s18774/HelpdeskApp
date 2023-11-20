package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "sla_id")
    private SLA sla;

}
