package pl.wroblewski.helpdeskapp.dto.application;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wroblewski.helpdeskapp.models.SLA;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationDto {
    private Integer applicationId;
    private Short sla;
    private String subject;
    private LocalDate openingDate;
    private String fullName;
}
