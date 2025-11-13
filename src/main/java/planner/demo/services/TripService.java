package planner.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.demo.models.Trip;
import planner.demo.repositories.TripRepository;

import java.util.List;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepo;

    public Trip createTrip(Trip trip) {
        tripRepo.save(trip);
        return trip;
    }

    public List<Trip> getAllTrips() {
        return tripRepo.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepo.findById(id).orElse(null);
    }

    public Trip updateTrip(Long id, Trip updatedTrip) {
        return tripRepo.findById(id).map(trip -> {
            trip.setTripName(updatedTrip.getTripName());
            trip.setStartDate(updatedTrip.getStartDate());
            trip.setEndDate(updatedTrip.getEndDate());
            trip.setLocationId(updatedTrip.getLocationId());
            trip.setUserId(updatedTrip.getUserId());
            trip.setExpenseId(updatedTrip.getExpenseId());
            return tripRepo.save(trip);
        }).orElse(null);
    }

    public void deleteTrip(Long id) {
        tripRepo.deleteById(id);
    }
}
