package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.UserApplication;
import pl.wroblewski.helpdeskapp.models.UserApplicationId;

public interface UserApplicationRepository extends CrudRepository<UserApplication, UserApplicationId> {
    //wyszukujemy wnioski konkretnego użytkownika
}
