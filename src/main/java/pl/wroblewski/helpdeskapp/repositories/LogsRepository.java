package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Logs;

public interface LogsRepository extends CrudRepository<Logs, Integer> {
}
