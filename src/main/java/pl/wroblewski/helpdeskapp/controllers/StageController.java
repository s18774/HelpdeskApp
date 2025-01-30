package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wroblewski.helpdeskapp.dto.stage.StageDto;
import pl.wroblewski.helpdeskapp.services.StageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stage")
@CrossOrigin("https://frontend.wonderfulground-93721921.polandcentral.azurecontainerapps.io")
@RequiredArgsConstructor
public class StageController extends BaseController {
    private final StageService stageService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<StageDto>> getAllStages() {
        return ResponseEntity.ok(stageService
                .getAllStages()
                .stream()
                .map(s -> modelMapper.map(s, StageDto.class))
                .toList());
    }
}
