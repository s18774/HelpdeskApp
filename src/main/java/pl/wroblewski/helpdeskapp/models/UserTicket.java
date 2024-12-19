package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "UserTicket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTicket {
    @EmbeddedId
    private UserTicketId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("ticketId")
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stageId;

    @ManyToOne
    @JoinColumn(name = "helpdesk_id")
    private User helpDeskId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Column(name = "opening_date", nullable = true)
    private LocalDate openingDate;

    @Column(name = "closing_date", nullable = true)//może być puste
    private LocalDate closingDate;

    @Column(name = "deadline_date", nullable = true)
    private LocalDate deadlineDate;

    @ManyToOne
    @JoinColumn(name = "resolver_user", nullable = true)
    private User resolverUser;
}
