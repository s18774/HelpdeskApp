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

    @Column(name = "ticket_number")
    private Integer ticketNumber;

    @Column(name = "building_name", length = 3)
    private String buildingName;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "room_number")
    private Integer roomNumber;

    @Column(name = "description", length = 300)
    private String description;

}
