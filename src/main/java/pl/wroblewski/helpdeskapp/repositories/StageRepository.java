package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Stage;

import java.util.List;
import java.util.Optional;

public interface StageRepository extends CrudRepository<Stage, Integer> {

    Optional<Stage> findByStageName(String stageName);// wyszukiwanie wniosków/zgłoszeń po konkretnym statusie(otwarte/zamknięte)
}
