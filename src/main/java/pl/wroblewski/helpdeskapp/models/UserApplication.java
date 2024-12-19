package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "UserApplication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApplication {
    @EmbeddedId
    private UserApplicationId id;

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("applicationId")
    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stageId;

    @ManyToOne
    @JoinColumn(name = "helpdesk_id")
    private User helpDeskId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "closing_date", nullable = true)
    private LocalDate closingDate;

    @ManyToOne
    @JoinColumn(name = "resolver_user", nullable = true)
    private User resolverUser;
}
