package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private HelpDesk helpDeskId;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @Column(name = "closing_date", nullable = true)//może być puste
    private LocalDate closingDate;
}
