package planner.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planner.demo.models.Trip;
import planner.demo.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    // Find all trips owned by a user
    List<Trip> findByOwner(User owner);

    // Find all trips where user is owner OR collaborator
    @Query("SELECT DISTINCT t FROM Trip t " +
            "LEFT JOIN t.collaborators c " +
            "WHERE t.owner = :user OR c = :user")
    List<Trip> findAllTripsForUser(@Param("user") User user);

    // Find trip by ID and check if user has access
    @Query("SELECT t FROM Trip t " +
            "LEFT JOIN t.collaborators c " +
            "WHERE t.id = :tripId AND (t.owner = :user OR c = :user)")
    Optional<Trip> findByIdAndUserHasAccess(
            @Param("tripId") Long tripId,
            @Param("user") User user
    );

    // Find trips by destination (optional - for search feature)
    List<Trip> findByDestinationContainingIgnoreCase(String destination);

    // Find upcoming trips for a user
    @Query("SELECT DISTINCT t FROM Trip t " +
            "LEFT JOIN t.collaborators c " +
            "WHERE (t.owner = :user OR c = :user) " +
            "AND t.startDate >= CURRENT_DATE " +
            "ORDER BY t.startDate ASC")
    List<Trip> findUpcomingTripsForUser(@Param("user") User user);
}
