package pl.wroblewski.helpdeskapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.wroblewski.helpdeskapp.dto.UserCredentials;
import pl.wroblewski.helpdeskapp.services.UserService;
import pl.wroblewski.helpdeskapp.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController extends BaseController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody UserCredentials userCredentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
        return ResponseEntity.ok(jwtTokenUtil.createToken(userService.getUser(userCredentials.getUsername())));
    }
}
