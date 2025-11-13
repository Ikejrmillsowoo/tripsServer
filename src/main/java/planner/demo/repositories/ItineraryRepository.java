package planner.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Itinerary;

@Repository
public interface ItineraryRepository  extends JpaRepository<Itinerary, Long> {

}
