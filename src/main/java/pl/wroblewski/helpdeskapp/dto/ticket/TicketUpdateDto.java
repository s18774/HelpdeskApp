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
    private Integer slaId;
    private String title;
    private String description;
    private Integer stageId;
    private Integer helpdeskId;
}
