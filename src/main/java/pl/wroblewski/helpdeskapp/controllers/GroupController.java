package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.GroupDto;
import pl.wroblewski.helpdeskapp.services.GroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class GroupController extends BaseController {

    private final GroupService groupService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllUsers() {
        var groups = groupService.getAllGroups().stream().map(group -> modelMapper.map(group, GroupDto.class)).toList();
        return ResponseEntity.ok(groups);
    }
}
