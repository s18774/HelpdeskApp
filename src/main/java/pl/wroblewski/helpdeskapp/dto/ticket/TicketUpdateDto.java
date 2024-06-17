package pl.wroblewski.helpdeskapp.dto.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class TicketUpdateDto {
    private Integer ticketId;
    private Short sla;
    private String title;
    private Integer stageId;
}
