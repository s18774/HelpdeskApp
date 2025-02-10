package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Group;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    List<Group> findAllByOrderByGroupName();
}
