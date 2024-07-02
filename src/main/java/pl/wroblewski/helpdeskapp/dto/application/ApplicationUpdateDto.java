package pl.wroblewski.helpdeskapp.dto.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationUpdateDto {
    private Integer applicationId;
    private Integer slaId;
    private String subject;
    private String description;
    private Integer stageId;
    private Integer groupId;
    private Integer helpdeskId;
}
