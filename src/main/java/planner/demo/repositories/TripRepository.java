package planner.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
}
