package pl.wroblewski.helpdeskapp.dto.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketCreateDto {
    private Integer slaId;
    private Integer departmentId;
    private Integer userId;
    private Integer helpdeskId;
    private Integer groupId;
    private Integer floor;
    private String title;
    private String description;
}