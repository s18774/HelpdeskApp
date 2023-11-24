package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {//application aka wniosek xD, idk jak to przetłumaczyć lepiej
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer applicationId;

    @ManyToOne
    @JoinColumn(name = "sla_id")
    private SLA sla;

    @Column(name = "subject", length = 50)
    private String subject;

    @Column(name = "type_of_application", length = 30)
    private String typeOfApplication;

    @Column(name = "application_number")
    private Long applicationNumber;

    @Column(name = "description", length = 300)
    private String description;

}
