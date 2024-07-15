package pl.wroblewski.helpdeskapp.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private Integer userId;
    private String firstName;
    private String secondName;
    private String positionName;
    private Integer groupId;
    private Integer supervisorId;
    private Integer departmentId;
    private Integer roleId;
    private String phoneNumber;
    private String username;
    private String email;
    private Integer floor;
    private Integer room;
}
