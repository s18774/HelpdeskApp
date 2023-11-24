package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.security.PrivateKey;

@Entity(name = "HelpDesk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelpDesk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "helpdesk_id")
    private Integer helpDeskId;

    @ManyToOne
    @JoinColumn(name = "exp_id")
    private ExperienceLevel expId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
