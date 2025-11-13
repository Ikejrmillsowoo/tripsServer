package planner.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Trips;

@Repository
public interface TripsRepository extends CrudRepository<Trips, Long> {
}
