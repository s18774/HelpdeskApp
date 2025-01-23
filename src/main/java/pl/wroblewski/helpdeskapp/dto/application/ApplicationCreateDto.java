package pl.wroblewski.helpdeskapp.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationCreateDto {
    private Integer slaId;

    @NotNull
    private Integer userId;
    private Integer helpdeskId;
    private Integer groupId;

    @NotBlank
    private String title;
    private String description;
}
