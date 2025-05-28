package pl.wroblewski.helpdeskapp.models;

import jakarta.persistence.*;
import lombok.*;
import pl.wroblewski.helpdeskapp.converters.LocalDateTimeStringConverter;

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
    @Column(name = "logs_id", nullable = false)
    private Integer logsId;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "date")
    @Convert(converter = LocalDateTimeStringConverter.class)
    private LocalDateTime date;
}
