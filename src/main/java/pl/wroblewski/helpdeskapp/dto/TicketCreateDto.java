package pl.wroblewski.helpdeskapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketCreateDto {
    private Integer slaId;
    private Integer departmentId;
    private String title;
    private String description;
}