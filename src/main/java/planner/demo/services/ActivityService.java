package planner.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planner.demo.models.Activity;
import planner.demo.models.Trip;
import planner.demo.models.User;
import planner.demo.repositories.ActivityRepository;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final TripService tripService;

    public Activity createActivity(Long tripId, Activity activity, User user) {
        Trip trip = tripService.getTripById(tripId, user);

        // Validate day is within trip duration
        long tripDuration = ChronoUnit.DAYS.between(trip.getStartDate(), trip.getEndDate()) + 1;
        if (activity.getDay() < 1 || activity.getDay() > tripDuration) {
            throw new RuntimeException("Day must be between 1 and " + tripDuration);
        }

        activity.setTrip(trip);
        return activityRepository.save(activity);
    }

    public List<Activity> getAllActivitiesForTrip(Long tripId, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        return activityRepository.findByTripOrderByDayAscTimeAsc(trip);
    }

    public List<Activity> getActivitiesByDay(Long tripId, Integer day, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        return activityRepository.findByTripAndDayOrderByTimeAsc(trip, day);
    }

    public Activity getActivityById(Long activityId, User user) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        // Verify user has access to the trip
        tripService.getTripById(activity.getTrip().getId(), user);

        return activity;
    }

    public Activity updateActivity(Long activityId, Activity updatedActivity, User user) {
        Activity activity = getActivityById(activityId, user);
        Trip trip = activity.getTrip();

        if (updatedActivity.getName() != null) {
            activity.setName(updatedActivity.getName());
        }
        if (updatedActivity.getDay() != null) {
            // Validate day
            long tripDuration = ChronoUnit.DAYS.between(trip.getStartDate(), trip.getEndDate()) + 1;
            if (updatedActivity.getDay() < 1 || updatedActivity.getDay() > tripDuration) {
                throw new RuntimeException("Day must be between 1 and " + tripDuration);
            }
            activity.setDay(updatedActivity.getDay());
        }
        if (updatedActivity.getTime() != null) {
            activity.setTime(updatedActivity.getTime());
        }
        if (updatedActivity.getLocation() != null) {
            activity.setLocation(updatedActivity.getLocation());
        }
        if (updatedActivity.getCost() != null) {
            activity.setCost(updatedActivity.getCost());
        }

        return activityRepository.save(activity);
    }

    public void deleteActivity(Long activityId, User user) {
        Activity activity = getActivityById(activityId, user);
        activityRepository.delete(activity);
    }

    public long countActivitiesForTrip(Long tripId, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        return activityRepository.countByTrip(trip);
    }
}
