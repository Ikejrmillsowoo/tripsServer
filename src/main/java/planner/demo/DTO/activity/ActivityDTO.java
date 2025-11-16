package planner.demo.DTO.activity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private Long tripId;
    private Integer day;
    private String name;
    private LocalTime time;
    private String location;
    private BigDecimal cost;
    private Instant createdAt;
}
