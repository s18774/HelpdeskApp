package pl.wroblewski.helpdeskapp.dto.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationDto {
    private Integer applicationId;
    private Short sla;
    private String subject;
    private String description;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private String fullName;
    private Integer helpdeskId;
    private Integer groupId;
    private Integer stageId;
    private Integer userId;
}
