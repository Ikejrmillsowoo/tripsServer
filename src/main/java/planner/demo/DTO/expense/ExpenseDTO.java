package planner.demo.DTO.expense;

import planner.demo.DTO.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long id;
    private Long tripId;
    private String description;
    private BigDecimal amount;
    private UserDTO paidBy;
    private Set<UserDTO> splitBetween = new HashSet<>();
    private Instant createdAt;
}