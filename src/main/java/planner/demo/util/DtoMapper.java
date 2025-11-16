package planner.demo.util;

import planner.demo.DTO.activity.ActivityDTO;
import planner.demo.DTO.expense.ExpenseDTO;
import planner.demo.DTO.trip.TripDTO;
import planner.demo.DTO.user.UserDTO;
import planner.demo.models.Activity;
import planner.demo.models.Expense;
import planner.demo.models.Trip;
import planner.demo.models.User;

import java.util.stream.Collectors;

public class DtoMapper {

    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getCreatedAt()
        );
    }

    public static TripDTO toTripDTO(Trip trip) {
        if (trip == null) return null;

        TripDTO dto = new TripDTO();
        dto.setId(trip.getId());
        dto.setName(trip.getName());
        dto.setDestination(trip.getDestination());
        dto.setStartDate(trip.getStartDate());
        dto.setEndDate(trip.getEndDate());
        dto.setOwner(toUserDTO(trip.getOwner()));
        dto.setCollaborators(
                trip.getCollaborators().stream()
                        .map(DtoMapper::toUserDTO)
                        .collect(Collectors.toList())
        );
        dto.setActivityCount(trip.getActivities().size());
        dto.setExpenseCount(trip.getExpenses().size());
        dto.setCreatedAt(trip.getCreatedAt());
        dto.setUpdatedAt(trip.getUpdatedAt());

        return dto;
    }

    public static ActivityDTO toActivityDTO(Activity activity) {
        if (activity == null) return null;

        return new ActivityDTO(
                activity.getId(),
                activity.getTrip().getId(),
                activity.getDay(),
                activity.getName(),
                activity.getTime(),
                activity.getLocation(),
                activity.getCost(),
                activity.getCreatedAt()
        );
    }

    public static ExpenseDTO toExpenseDTO(Expense expense) {
        if (expense == null) return null;

        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setTripId(expense.getTrip().getId());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount());
        dto.setPaidBy(toUserDTO(expense.getPaidBy()));
        dto.setSplitBetween(
                expense.getSplitBetween().stream()
                        .map(DtoMapper::toUserDTO)
                        .collect(Collectors.toSet())
        );
        dto.setCreatedAt(expense.getCreatedAt());

        return dto;
    }
}
//```
//
//        ---
//
//        ## 📋 DTO Package Structure
//```
//dto/
//        ├── auth/
//        │   ├── RegisterRequest.java
//│   ├── LoginRequest.java
//│   └── AuthResponse.java
//├── user/
//        │   └── UserDTO.java
//├── trip/
//        │   ├── CreateTripRequest.java
//│   ├── UpdateTripRequest.java
//│   ├── TripDTO.java
//│   └── AddCollaboratorRequest.java
//├── activity/
//        │   ├── CreateActivityRequest.java
//│   ├── UpdateActivityRequest.java
//│   └── ActivityDTO.java
//├── expense/
//        │   ├── CreateExpenseRequest.java
//│   ├── UpdateExpenseRequest.java
//│   ├── ExpenseDTO.java
//│   └── ExpenseSummaryDTO.java
//└── common/
//        ├── ApiResponse.java
//    └── ErrorResponse.java