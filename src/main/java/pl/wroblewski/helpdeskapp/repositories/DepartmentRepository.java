package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
}
