package planner.demo.DTO.trip;

import planner.demo.DTO.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private String name;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserDTO owner;
    private List<UserDTO> collaborators = new ArrayList<>();
    private Integer activityCount;
    private Integer expenseCount;
    private Instant createdAt;
    private Instant updatedAt;
}