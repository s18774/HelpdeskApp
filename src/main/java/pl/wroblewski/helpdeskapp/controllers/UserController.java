package pl.wroblewski.helpdeskapp.controllers;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.ExperienceLevelDto;
import pl.wroblewski.helpdeskapp.dto.role.RoleDto;
import pl.wroblewski.helpdeskapp.dto.user.UserCreateDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDetailsDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDto;
import pl.wroblewski.helpdeskapp.dto.user.UserUpdateDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.InvalidRoleException;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin( origins = {"https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io",
        "http://localhost:3000"})
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException {
        User author = userService.getUser(userDetails.getUsername());
        var users = userService.getAllUsers(author.getUserId())
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/details")
    public ResponseEntity<List<UserDetailsDto>> getAllDetailedUsers(@PathParam("firstName") String firstName,
                                                                    @PathParam("secondName") String secondName,
                                                                    @PathParam("positionName") String positionName,
                                                                    @PathParam("groupName") String groupName,
                                                                    @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        var users = userService.getAllUsersDetails(firstName, secondName, positionName, groupName, author.getUserId())
                .stream()
                .map(user -> modelMapper.map(user, UserDetailsDto.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable Integer userId,
                                                  @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        User user = userService.getUser(userId, author.getUserId());
        return ResponseEntity.ok(modelMapper.map(user, UserDetailsDto.class));
    }

    @GetMapping("/helpdesk")
    public ResponseEntity<List<UserDto>> getAllHelpdesk() throws EntityNotExists {
        var users = userService.getAllHelpdesk().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto,
                                              @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException, InvalidRoleException {
        User author = userService.getUser(userDetails.getUsername());
        User newUser = modelMapper.map(userCreateDto, User.class);
        newUser = userService.createUser(newUser, userCreateDto.getGroupId(), userCreateDto.getDepartmentId(),
                userCreateDto.getUserId(), userCreateDto.getRoleId(), userCreateDto.getExperienceLevelId(),
                author.getUserId());
        return new ResponseEntity<>(modelMapper.map(newUser, UserDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        var roles = userService.getAllRoles()
                .stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .toList();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/experience-levels")
    public ResponseEntity<List<ExperienceLevelDto>> getAllExperienceLevels() {
        var experienceLevels = userService.getAllExperienceLevels()
                .stream()
                .map(exp -> modelMapper.map(exp, ExperienceLevelDto.class))
                .toList();
        return ResponseEntity.ok(experienceLevels);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateUser(@RequestBody @Valid UserUpdateDto user,
                                                   @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        userService.updateUser(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getSecondName(),
                user.getPositionName(),
                user.getGroupId(),
                user.getSupervisorId(),
                user.getDepartmentId(),
                user.getRoleId(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getFloor(),
                user.getRoom(),
                user.getExperienceLevelId(),
                author.getUserId());

        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("User created!")
                .build());
    }
}
