package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wroblewski.helpdeskapp.dto.SLADto;
import pl.wroblewski.helpdeskapp.dto.UserDto;
import pl.wroblewski.helpdeskapp.services.SLAService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/sla")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class SLAController {
    private final SLAService slaService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SLADto>> getAllSla() {
        var sla = slaService.getAllSLA().stream().map(user -> modelMapper.map(user, SLADto.class)).toList();
        return ResponseEntity.ok(sla);
    }
}
