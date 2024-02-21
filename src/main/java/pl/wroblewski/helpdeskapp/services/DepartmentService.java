package pl.wroblewski.helpdeskapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wroblewski.helpdeskapp.models.Department;
import pl.wroblewski.helpdeskapp.models.SLA;
import pl.wroblewski.helpdeskapp.repositories.DepartmentRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository deoartmentReposiotry;

    public List<Department> getAllDepartments() {
        return (List<Department>) deoartmentReposiotry.findAll();
    }
}
