package pl.wroblewski.helpdeskapp.controllers;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.device.DeviceCreateDto;
import pl.wroblewski.helpdeskapp.dto.device.DeviceDto;
import pl.wroblewski.helpdeskapp.dto.role.RoleDto;
import pl.wroblewski.helpdeskapp.dto.ticket.TicketDto;
import pl.wroblewski.helpdeskapp.dto.user.UserCreateDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDetailsDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.models.UserTicket;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException {
        User author = userService.getUser(userDetails.getUsername());
        var users = userService.getAllUsers(author.getUserId()).stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/details")
    public ResponseEntity<List<UserDetailsDto>> getAllDetailedUsers(@PathParam("firstName") String firstName,
                                                                    @PathParam("secondName") String secondName,
                                                                    @PathParam("positionName") String positionName,
                                                                    @PathParam("groupName") String groupName,
                                                                    @AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        var users = userService.getAllUsersDetails(firstName, secondName, positionName, groupName, author.getUserId()).stream().map(user -> modelMapper.map(user, UserDetailsDto.class)).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable Integer userId, @AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException, PermissionsException, EntityNotExists {
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
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto userCreateDto,
                                                  @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        User newUser = modelMapper.map(userCreateDto, User.class);
        newUser = userService.createUser(newUser, userCreateDto.getGroupId(), userCreateDto.getDepartmentId(), userCreateDto.getUserId(), userCreateDto.getRoleId(), author.getUserId());
        return ResponseEntity.ok(modelMapper.map(newUser, UserDto.class));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles(@AuthenticationPrincipal UserDetails userDetails) throws UserNotExistsException {
        var roles = userService.getAllRoles()
                .stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .toList();
        return ResponseEntity.ok(roles);
    }
}
