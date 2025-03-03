package pl.wroblewski.helpdeskapp.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserUpdateDto {
    private Integer userId;

    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String secondName;

    @Size(max = 25)
    private String positionName;

    private Integer groupId;
    private Integer supervisorId;
    private Integer departmentId;
    private Integer roleId;

    @Size(max = 12)
    private String phoneNumber;

    @Size(max = 20)
    private String username;

    @Size(max = 50)
    private String email;
    private Integer floor;
    private Integer room;
    private Integer experienceLevelId;
}
