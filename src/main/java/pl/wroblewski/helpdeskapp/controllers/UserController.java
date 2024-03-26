package pl.wroblewski.helpdeskapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.UserDto;
import pl.wroblewski.helpdeskapp.exceptions.EntityNotExists;
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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getAllUsers().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/helpdesk")
    public ResponseEntity<List<UserDto>> getAllHelpdesk() throws EntityNotExists {
        var users = userService.getAllHelpdesk().stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(users);
    }
}
