package pl.wroblewski.helpdeskapp.dto.ticket;

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
    private LocalDate closingDate;
    private String title;
    private String description;
    private String fullName;
    private Integer stageId;
    private Integer helpdeskId;
    private Integer userId;
    private String resolverUser;
    private Integer groupId;
}
