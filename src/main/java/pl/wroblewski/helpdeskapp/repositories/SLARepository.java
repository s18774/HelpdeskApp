package pl.wroblewski.helpdeskapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.wroblewski.helpdeskapp.models.SLA;

import java.util.List;

public interface SLARepository extends CrudRepository<SLA, Integer> {
    List<SLA> findAll();
    List<SLA> findAllBySlaLevelOrderBySlaIdDesc(Short slaLevel);
    List<SLA> findAllBySlaLevelGreaterThan(Short slaLevel);
}
