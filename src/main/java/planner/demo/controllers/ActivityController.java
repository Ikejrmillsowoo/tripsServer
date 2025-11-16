package planner.demo.controllers;

import planner.demo.DTO.activity.ActivityDTO;
import planner.demo.DTO.activity.CreateActivityRequest;
import planner.demo.DTO.activity.UpdateActivityRequest;
import planner.demo.DTO.common.ApiResponse;
import planner.demo.models.Activity;
import planner.demo.models.User;
import planner.demo.security.CurrentUser;
import planner.demo.services.ActivityService;
import planner.demo.util.DtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips/{tripId}/activities")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ApiResponse<ActivityDTO>> createActivity(
            @PathVariable Long tripId,
            @Valid @RequestBody CreateActivityRequest request,
            @CurrentUser User currentUser) {

        Activity activity = new Activity();
        activity.setDay(request.getDay());
        activity.setName(request.getName());
        activity.setTime(request.getTime());
        activity.setLocation(request.getLocation());
        activity.setCost(request.getCost());

        Activity createdActivity = activityService.createActivity(tripId, activity, currentUser);
        ActivityDTO dto = DtoMapper.toActivityDTO(createdActivity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Activity created successfully", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityDTO>>> getAllActivities(
            @PathVariable Long tripId,
            @CurrentUser User currentUser) {

        List<Activity> activities = activityService.getAllActivitiesForTrip(tripId, currentUser);
        List<ActivityDTO> dtos = activities.stream()
                .map(DtoMapper::toActivityDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<ApiResponse<List<ActivityDTO>>> getActivitiesByDay(
            @PathVariable Long tripId,
            @PathVariable Integer day,
            @CurrentUser User currentUser) {

        List<Activity> activities = activityService.getActivitiesByDay(tripId, day, currentUser);
        List<ActivityDTO> dtos = activities.stream()
                .map(DtoMapper::toActivityDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ApiResponse<ActivityDTO>> updateActivity(
            @PathVariable Long tripId,
            @PathVariable Long activityId,
            @Valid @RequestBody UpdateActivityRequest request,
            @CurrentUser User currentUser) {

        Activity updatedActivity = new Activity();
        updatedActivity.setDay(request.getDay());
        updatedActivity.setName(request.getName());
        updatedActivity.setTime(request.getTime());
        updatedActivity.setLocation(request.getLocation());
        updatedActivity.setCost(request.getCost());

        Activity activity = activityService.updateActivity(activityId, updatedActivity, currentUser);
        ActivityDTO dto = DtoMapper.toActivityDTO(activity);

        return ResponseEntity.ok(ApiResponse.success("Activity updated successfully", dto));
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<ApiResponse<Void>> deleteActivity(
            @PathVariable Long tripId,
            @PathVariable Long activityId,
            @CurrentUser User currentUser) {

        activityService.deleteActivity(activityId, currentUser);

        return ResponseEntity.ok(ApiResponse.success("Activity deleted successfully", null));
    }
}