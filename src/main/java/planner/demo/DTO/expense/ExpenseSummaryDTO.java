package planner.demo.DTO.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSummaryDTO {
    private BigDecimal totalExpenses;
    private Map<String, UserExpenseSummary> userSummaries = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserExpenseSummary {
        private String email;
        private String name;
        private BigDecimal paid;
        private BigDecimal owes;
        private BigDecimal balance;
    }
}
