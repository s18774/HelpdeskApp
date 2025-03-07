package pl.wroblewski.helpdeskapp.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobDto {
    private String title;
    private String jobType;
    private Integer jobId;
    private Integer jobNumber;
    private String fullName;
    private Integer sla;
    private Integer stageId;
    private Integer userId;
}
