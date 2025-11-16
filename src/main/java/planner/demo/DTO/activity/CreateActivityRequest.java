package planner.demo.DTO.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class CreateActivityRequest {

    @NotNull(message = "Day is required")
    @Positive(message = "Day must be positive")
    private Integer day;

    @NotBlank(message = "Activity name is required")
    private String name;

    private LocalTime time;

    private String location;

    private BigDecimal cost;
}
