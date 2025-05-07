package pl.wroblewski.helpdeskapp.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wroblewski.helpdeskapp.dto.DepartmentDto;
import pl.wroblewski.helpdeskapp.services.DepartmentService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/department")
@CrossOrigin( origins = {"https://helpdeskapp-pd29.onrender.com",
        "http://localhost:3000"})
@RequiredArgsConstructor
public class DepartmentController extends BaseController {
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        var sla = departmentService.getAllDepartments().stream().map(
                user -> modelMapper.map(user, DepartmentDto.class)).toList();
        return ResponseEntity.ok(sla);
    }
}
