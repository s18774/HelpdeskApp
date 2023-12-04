package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.UserApplication;

public interface UserApplicationRepository extends CrudRepository<UserApplication, Integer> {
    //wyszukujemy wnioski konkretnego użytkownika
}
