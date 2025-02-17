package pl.wroblewski.helpdeskapp.dto.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Integer groupId;
    private String roomNumber;
    private Integer floor;
}
