package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.Application;

public interface ApplicationRepository extends CrudRepository<Application, Integer> {
    @Query(value = "SELECT MAX(a.applicationNumber) FROM Application a")
    Integer findMaxApplicationNumber();
}
