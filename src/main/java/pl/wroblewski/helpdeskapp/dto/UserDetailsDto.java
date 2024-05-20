package pl.wroblewski.helpdeskapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDetailsDto {
    private Integer userId;
    private String firstName;
    private String secondName;
    private String groupName;
    private String positionName;
}
