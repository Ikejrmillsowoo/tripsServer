package planner.demo.DTO.trip;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTripRequest {
    private String name;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
}
