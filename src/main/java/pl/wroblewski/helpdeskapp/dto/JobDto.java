package pl.wroblewski.helpdeskapp.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobDto {
    private String jobType;
    private Integer id;
    private String fullName;
    private Integer sla;
}
