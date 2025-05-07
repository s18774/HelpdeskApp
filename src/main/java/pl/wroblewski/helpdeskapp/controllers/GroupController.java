package pl.wroblewski.helpdeskapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.BaseResponse;
import pl.wroblewski.helpdeskapp.dto.group.GroupCreateDto;
import pl.wroblewski.helpdeskapp.dto.group.GroupDto;
import pl.wroblewski.helpdeskapp.dto.group.GroupUpdateDto;
import pl.wroblewski.helpdeskapp.dto.user.UserDetailsDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.Group;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.GroupService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@CrossOrigin( origins = {"https://helpdeskappfront.onrender.com",
        "http://localhost:3000"})
@RequiredArgsConstructor
public class GroupController extends BaseController {

    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        var groups = groupService.getAllGroups().stream().map(group -> modelMapper.map(group, GroupDto.class)).toList();
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody @Valid GroupCreateDto groupCreateDto,
                                                @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        var newGroup = groupService.createGroup(groupCreateDto.getGroupName(), author.getUserId());
        return new ResponseEntity<>(modelMapper.map(newGroup, GroupDto.class), HttpStatus.CREATED);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<GroupDto> getGroup(@PathVariable Integer groupId,
                                             @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        Group group = groupService.getGroup(groupId, author.getUserId());
        return ResponseEntity.ok(modelMapper.map(group, GroupDto.class));
    }

    @GetMapping("{groupId}/users")
    public ResponseEntity<List<UserDetailsDto>> getGroupUsers(@PathVariable Integer groupId,
                                                              @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        List<User> users = groupService.getGroupUsers(groupId, author.getUserId());
        return ResponseEntity.ok(users
                .stream()
                .map(u -> modelMapper.map(u, UserDetailsDto.class))
                .toList());
    }

    @PostMapping("{groupId}/users/{userId}")
    public ResponseEntity<BaseResponse> addUserToGroup(@PathVariable Integer groupId, @PathVariable Integer userId,
                                                       @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        groupService.addUser(groupId, userId, author.getUserId());
        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Done")
                .build());
    }

    @DeleteMapping("{groupId}/users/{userId}")
    public ResponseEntity<BaseResponse> removeUserFromGroup(@PathVariable Integer groupId, @PathVariable Integer userId,
                                                            @AuthenticationPrincipal UserDetails userDetails)
            throws UserNotExistsException, PermissionsException, EntityNotExists {
        User author = userService.getUser(userDetails.getUsername());
        groupService.removeUser(groupId, userId, author.getUserId());
        return ResponseEntity.ok(BaseResponse.builder()
                .success(true)
                .message("Done")
                .build());
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateGroup(@RequestBody GroupUpdateDto group,
                                                    @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());

        groupService.updateGroup(group.getGroupId(), group.getGroupName(), group.getIsGroupActive(), author.getUserId());

        return new ResponseEntity<>(BaseResponse.builder()
                .success(true)
                .message("Group updated!")
                .build(), HttpStatus.CREATED);
    }
}
