package planner.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planner.demo.models.Trip;
import planner.demo.models.User;
import planner.demo.repositories.TripRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    private final TripRepository tripRepository;
    private final UserService userService;

    public Trip createTrip(Trip trip, User owner) {
        trip.setOwner(owner);
        trip.getCollaborators().add(owner); // Owner is also a collaborator

        // Validate dates
        if (trip.getEndDate().isBefore(trip.getStartDate())) {
            throw new RuntimeException("End date cannot be before start date");
        }

        return tripRepository.save(trip);
    }

    public List<Trip> getAllTripsForUser(User user) {
        return tripRepository.findAllTripsForUser(user);
    }

    public List<Trip> getUpcomingTripsForUser(User user) {
        return tripRepository.findUpcomingTripsForUser(user);
    }

    public Trip getTripById(Long tripId, User user) {
        return tripRepository.findByIdAndUserHasAccess(tripId, user)
                .orElseThrow(() -> new RuntimeException("Trip not found or access denied"));
    }

    public Trip updateTrip(Long tripId, Trip updatedTrip, User user) {
        Trip trip = getTripById(tripId, user);

        // Only owner can update trip details
        if (!trip.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Only trip owner can update trip details");
        }

        if (updatedTrip.getName() != null) {
            trip.setName(updatedTrip.getName());
        }
        if (updatedTrip.getDestination() != null) {
            trip.setDestination(updatedTrip.getDestination());
        }
        if (updatedTrip.getStartDate() != null) {
            trip.setStartDate(updatedTrip.getStartDate());
        }
        if (updatedTrip.getEndDate() != null) {
            trip.setEndDate(updatedTrip.getEndDate());
        }

        // Validate dates
        if (trip.getEndDate().isBefore(trip.getStartDate())) {
            throw new RuntimeException("End date cannot be before start date");
        }

        return tripRepository.save(trip);
    }

    public void deleteTrip(Long tripId, User user) {
        Trip trip = getTripById(tripId, user);

        // Only owner can delete trip
        if (!trip.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Only trip owner can delete the trip");
        }

        tripRepository.delete(trip);
    }

    public Trip addCollaborator(Long tripId, String collaboratorEmail, User user) {
        Trip trip = getTripById(tripId, user);

        // Only owner can add collaborators
        if (!trip.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Only trip owner can add collaborators");
        }

        User collaborator = userService.findByEmail(collaboratorEmail);

        if (trip.getCollaborators().contains(collaborator)) {
            throw new RuntimeException("User is already a collaborator");
        }

        trip.getCollaborators().add(collaborator);
        return tripRepository.save(trip);
    }

    public Trip removeCollaborator(Long tripId, Long collaboratorId, User user) {
        Trip trip = getTripById(tripId, user);

        // Only owner can remove collaborators
        if (!trip.getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Only trip owner can remove collaborators");
        }

        // Cannot remove owner
        if (trip.getOwner().getId().equals(collaboratorId)) {
            throw new RuntimeException("Cannot remove trip owner");
        }

        User collaborator = userService.findById(collaboratorId);
        trip.getCollaborators().remove(collaborator);

        return tripRepository.save(trip);
    }

    public List<Trip> searchTripsByDestination(String destination, User user) {
        List<Trip> allTrips = tripRepository.findByDestinationContainingIgnoreCase(destination);
        // Filter to only trips the user has access to
        return allTrips.stream()
                .filter(trip -> trip.getOwner().equals(user) || trip.getCollaborators().contains(user))
                .toList();
    }

}
