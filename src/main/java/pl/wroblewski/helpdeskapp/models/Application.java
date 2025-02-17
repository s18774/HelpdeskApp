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
    @Column(name = "application_id", nullable = false)
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "sla_id")
    private SLA sla;

    @Column(name = "subject", length = 50, nullable = false)
    private String subject;

    @Column(name = "type_of_application", length = 30, nullable = false)
    private String typeOfApplication;

    @Column(name = "application_number", nullable = false)
    private Integer applicationNumber;

    @Column(name = "description", length = 300, nullable = false)
    private String description;

}
