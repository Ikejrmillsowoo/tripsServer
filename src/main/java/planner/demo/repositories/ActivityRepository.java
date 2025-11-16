package planner.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planner.demo.models.Activity;
import planner.demo.models.Trip;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // Find all activities for a trip
    List<Activity> findByTrip(Trip trip);

    // Find all activities for a trip, ordered by day and time
    List<Activity> findByTripOrderByDayAscTimeAsc(Trip trip);

    // Find activities by trip ID
    List<Activity> findByTripId(Long tripId);

    // Find activities for a specific day of a trip
    List<Activity> findByTripAndDayOrderByTimeAsc(Trip trip, Integer day);

    // Find activities within a cost range
    @Query("SELECT a FROM Activity a WHERE a.trip = :trip " +
            "AND a.cost BETWEEN :minCost AND :maxCost")
    List<Activity> findByTripAndCostRange(
            @Param("trip") Trip trip,
            @Param("minCost") java.math.BigDecimal minCost,
            @Param("maxCost") java.math.BigDecimal maxCost
    );

    // Count activities for a trip
    long countByTrip(Trip trip);

    // Delete all activities for a trip (handled by cascade, but useful for custom logic)
    void deleteByTrip(Trip trip);
}
