package pl.wroblewski.helpdeskapp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse {
    private boolean success;
    private String message;
}
