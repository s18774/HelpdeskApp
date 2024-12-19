package pl.wroblewski.helpdeskapp.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class LogsDto {
    private Integer logsId;
    private String description;
    private LocalDateTime date;
}
