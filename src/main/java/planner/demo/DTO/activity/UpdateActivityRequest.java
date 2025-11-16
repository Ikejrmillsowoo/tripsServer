package planner.demo.DTO.activity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
public class UpdateActivityRequest {
    private Integer day;
    private String name;
    private LocalTime time;
    private String location;
    private BigDecimal cost;
}