package pl.wroblewski.helpdeskapp.dto.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationUpdateDto {
    private Integer applicationId;
    private Integer slaId;
    @NotBlank
    private String subject;
    @NotBlank
    private String description;
    private Integer stageId;
    private Integer groupId;
    private Integer helpdeskId;
    @NotBlank
    private String typeOfApplication;
}
