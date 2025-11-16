package planner.demo.DTO.expense;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class UpdateExpenseRequest {
    private String description;
    private BigDecimal amount;
    private Long paidByUserId;
    private Set<Long> splitBetweenUserIds;
}
