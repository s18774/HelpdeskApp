package pl.wroblewski.helpdeskapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ApplicationCreateDto {
    private Integer slaId;
    private Integer userId;
    private Integer helpdeskId;
    private Integer groupId;
    private String title;
    private String description;
}
