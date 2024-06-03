package pl.wroblewski.helpdeskapp.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int userId;
    private String fullName;
}
