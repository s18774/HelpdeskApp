package pl.wroblewski.helpdeskapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserCreateDto {
    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String secondName;

    @NotBlank
    @Size(max = 25)
    private String positionName;
    private Integer groupId;
    @NotNull
    private Integer departmentId;

    @Size(max = 12)
    private String phoneNumber;

    @Size(max = 50)
    private String email;

    @Size(max = 20)
    @NotBlank
    private String username;

    private Integer floor;
    private Integer room;
    private Integer userId;

    @NotNull
    private Integer roleId;

    @Size(max = 100)
    @NotBlank
    private String password;

    @NotNull
    private Integer experienceLevelId;
}
