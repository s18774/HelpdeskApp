package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "sla_id")
    private SLA sla;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "ticket_number")
    private Integer ticketNumber;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "titile")
    private String title;

    @Column(name = "description", length = 300)
    private String description;

}
