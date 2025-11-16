package planner.demo.controllers;

import planner.demo.DTO.common.ApiResponse;
import planner.demo.DTO.trip.AddCollaboratorRequest;
import planner.demo.DTO.trip.CreateTripRequest;
import planner.demo.DTO.trip.TripDTO;
import planner.demo.DTO.trip.UpdateTripRequest;
import planner.demo.models.Trip;
import planner.demo.models.User;
import planner.demo.security.CurrentUser;
import planner.demo.services.TripService;
import planner.demo.util.DtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<ApiResponse<TripDTO>> createTrip(
            @Valid @RequestBody CreateTripRequest request,
            @CurrentUser User currentUser) {

        Trip trip = new Trip();
        trip.setName(request.getName());
        trip.setDestination(request.getDestination());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());

        Trip createdTrip = tripService.createTrip(trip, currentUser);
        TripDTO dto = DtoMapper.toTripDTO(createdTrip);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Trip created successfully", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripDTO>>> getAllTrips(
            @CurrentUser User currentUser) {

        List<Trip> trips = tripService.getAllTripsForUser(currentUser);
        List<TripDTO> dtos = trips.stream()
                .map(DtoMapper::toTripDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<List<TripDTO>>> getUpcomingTrips(
            @CurrentUser User currentUser) {

        List<Trip> trips = tripService.getUpcomingTripsForUser(currentUser);
        List<TripDTO> dtos = trips.stream()
                .map(DtoMapper::toTripDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripDTO>> getTripById(
            @PathVariable Long tripId,
            @CurrentUser User currentUser) {

        Trip trip = tripService.getTripById(tripId, currentUser);
        TripDTO dto = DtoMapper.toTripDTO(trip);

        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<ApiResponse<TripDTO>> updateTrip(
            @PathVariable Long tripId,
            @Valid @RequestBody UpdateTripRequest request,
            @CurrentUser User currentUser) {

        Trip updatedTrip = new Trip();
        updatedTrip.setName(request.getName());
        updatedTrip.setDestination(request.getDestination());
        updatedTrip.setStartDate(request.getStartDate());
        updatedTrip.setEndDate(request.getEndDate());

        Trip trip = tripService.updateTrip(tripId, updatedTrip, currentUser);
        TripDTO dto = DtoMapper.toTripDTO(trip);

        return ResponseEntity.ok(ApiResponse.success("Trip updated successfully", dto));
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<ApiResponse<Void>> deleteTrip(
            @PathVariable Long tripId,
            @CurrentUser User currentUser) {

        tripService.deleteTrip(tripId, currentUser);

        return ResponseEntity.ok(ApiResponse.success("Trip deleted successfully", null));
    }

    @PostMapping("/{tripId}/collaborators")
    public ResponseEntity<ApiResponse<TripDTO>> addCollaborator(
            @PathVariable Long tripId,
            @Valid @RequestBody AddCollaboratorRequest request,
            @CurrentUser User currentUser) {

        Trip trip = tripService.addCollaborator(tripId, request.getEmail(), currentUser);
        TripDTO dto = DtoMapper.toTripDTO(trip);

        return ResponseEntity.ok(ApiResponse.success("Collaborator added successfully", dto));
    }

    @DeleteMapping("/{tripId}/collaborators/{collaboratorId}")
    public ResponseEntity<ApiResponse<TripDTO>> removeCollaborator(
            @PathVariable Long tripId,
            @PathVariable Long collaboratorId,
            @CurrentUser User currentUser) {

        Trip trip = tripService.removeCollaborator(tripId, collaboratorId, currentUser);
        TripDTO dto = DtoMapper.toTripDTO(trip);

        return ResponseEntity.ok(ApiResponse.success("Collaborator removed successfully", dto));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TripDTO>>> searchTrips(
            @RequestParam String destination,
            @CurrentUser User currentUser) {

        List<Trip> trips = tripService.searchTripsByDestination(destination, currentUser);
        List<TripDTO> dtos = trips.stream()
                .map(DtoMapper::toTripDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }
}
