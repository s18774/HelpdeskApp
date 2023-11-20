package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logs_id")
    private Integer logsId;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "date")
    private LocalDateTime date;
}
