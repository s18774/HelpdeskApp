package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Stage;

import java.util.List;

public interface StageRepository extends CrudRepository<Stage, Integer> {

    List<Stage> findAllByStageName(String stageName);// wyszukiwanie wniosków/zgłoszeń po konkretnym statusie(otwarte/zamknięte)
}
