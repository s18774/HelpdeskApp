package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.group.GroupCreateDto;
import pl.wroblewski.helpdeskapp.dto.group.GroupDto;
import pl.wroblewski.helpdeskapp.dto.device.DeviceCreateDto;
import pl.wroblewski.helpdeskapp.dto.device.DeviceDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
import pl.wroblewski.helpdeskapp.exceptions.PermissionsException;
import pl.wroblewski.helpdeskapp.exceptions.UserNotExistsException;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.services.GroupService;
import pl.wroblewski.helpdeskapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class GroupController extends BaseController {

    private final GroupService groupService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllUsers() {
        var groups = groupService.getAllGroups().stream().map(group -> modelMapper.map(group, GroupDto.class)).toList();
        return ResponseEntity.ok(groups);
    }

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupCreateDto groupCreateDto,
                                                  @AuthenticationPrincipal UserDetails userDetails)
            throws EntityNotExists, UserNotExistsException, PermissionsException {
        User author = userService.getUser(userDetails.getUsername());
        var newGroup = groupService.createGroup(groupCreateDto.getGroupName(), author.getUserId());
        return ResponseEntity.ok(modelMapper.map(newGroup, GroupDto.class));
    }
}
