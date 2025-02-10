package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByRoleNameOrderByRoleName(String roleName);
    List<Role> findAllByOrderByRoleName();
}
