package pl.wroblewski.helpdeskapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.models.User;
import pl.wroblewski.helpdeskapp.utils.JwtTokenUtil;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping
    public ResponseEntity<?> auth() {


        //TODO: tutaj token do wygenerowania
        return ResponseEntity.ok("TOKEN");
    }
}
