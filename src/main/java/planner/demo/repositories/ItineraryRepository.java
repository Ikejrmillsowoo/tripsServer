package planner.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Itinerary;

@Repository
public interface ItineraryRepository  extends CrudRepository<Itinerary, Long> {

}
