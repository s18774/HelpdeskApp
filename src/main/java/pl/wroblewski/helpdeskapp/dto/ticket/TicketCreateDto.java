package pl.wroblewski.helpdeskapp.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketCreateDto {
    private Integer slaId;
    private Integer departmentId;

    @NotNull
    private Integer userId;
    private Integer helpdeskId;
    private Integer groupId;

    @NotNull
    private Integer floor;

    @NotBlank
    private String title;

    private String description;
}