package pl.wroblewski.helpdeskapp.repositories;


import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.ExperienceLevel;

public interface ExperienceLevelRepository extends CrudRepository<ExperienceLevel, Integer> {
}
