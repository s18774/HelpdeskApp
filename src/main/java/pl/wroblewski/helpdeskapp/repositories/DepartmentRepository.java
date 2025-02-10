package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Department;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    List<Department> findAllByOrderByBuilding();
}
