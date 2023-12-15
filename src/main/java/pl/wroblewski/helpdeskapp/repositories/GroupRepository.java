package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Group;

public interface GroupRepository extends CrudRepository<Group, Integer> {
    //tworzenie grup dla pracowników(administrator/lider)
    //wyświetlanie istniejących grup oraz pracowników znajdujących się w tych grupach
}
