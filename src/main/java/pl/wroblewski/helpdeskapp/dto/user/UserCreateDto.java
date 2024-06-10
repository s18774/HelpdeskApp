package pl.wroblewski.helpdeskapp.dto.user;

import lombok.*;
import pl.wroblewski.helpdeskapp.models.Department;
import pl.wroblewski.helpdeskapp.models.User;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserCreateDto {
    private String firstName;
    private String secondName;
    private String positionName;
    private Integer groupId;
    private Integer departmentId;
    private String phoneNumber;
    private String email;
    private String username;
    private Integer floor;
    private Integer room;
    private Integer userId;
    private Integer roleId;
    private String password;
}
