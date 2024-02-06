package pl.wroblewski.helpdeskapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TicketDto {
    private Integer ticketId;
    private Short sla;
    private LocalDate openingDate;
    private String title;
    private String fullName;
}
