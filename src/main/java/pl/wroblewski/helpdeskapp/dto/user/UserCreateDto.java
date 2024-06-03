package pl.wroblewski.helpdeskapp.dto.user;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserCreateDto {
    private String firstname;
    private String secondname;
    private String position;
    private Integer groupId;
}
