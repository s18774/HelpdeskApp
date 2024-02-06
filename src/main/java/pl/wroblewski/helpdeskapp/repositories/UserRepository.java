package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    //wyszukiwanie po imieniu
    //wyszukiwanie po nazwisku

    //edycja danych (administrator/lider)
    //tworzenie/usuwanie konta użytkownika(admin/lider)
}
