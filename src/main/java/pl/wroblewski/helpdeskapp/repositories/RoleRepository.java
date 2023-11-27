package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
