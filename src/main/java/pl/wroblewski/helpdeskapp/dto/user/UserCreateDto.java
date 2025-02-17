package pl.wroblewski.helpdeskapp.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserCreateDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    @NotBlank
    private String positionName;
    @NotNull
    private Integer groupId;
    @NotNull
    private Integer departmentId;
    private String phoneNumber;
    private String email;
    @NotBlank
    private String username;
    private Integer floor;
    private Integer room;
    private Integer userId;
    @NotNull
    private Integer roleId;
    @NotBlank
    private String password;
    @NotNull
    private Integer experienceLevelId;
}
