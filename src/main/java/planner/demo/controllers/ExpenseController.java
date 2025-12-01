package planner.demo.controllers;


import planner.demo.DTO.common.ApiResponse;
import planner.demo.DTO.expense.CreateExpenseRequest;
import planner.demo.DTO.expense.ExpenseDTO;
import planner.demo.DTO.expense.ExpenseSummaryDTO;
import planner.demo.DTO.expense.UpdateExpenseRequest;
import planner.demo.models.Expense;
import planner.demo.models.User;
import planner.demo.security.CurrentUser;
import planner.demo.services.ExpenseService;
import planner.demo.services.UserService;
import planner.demo.util.DtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips/{tripId}/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "${cors.allowed-origins}")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseDTO>> createExpense(
            @PathVariable Long tripId,
            @Valid @RequestBody CreateExpenseRequest request,
            @CurrentUser User currentUser) {

        Expense expense = new Expense();
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());

        User paidBy = userService.findById(request.getPaidByUserId());
        expense.setPaidBy(paidBy);

        Set<User> splitBetween = request.getSplitBetweenUserIds().stream()
                .map(userService::findById)
                .collect(Collectors.toSet());
        expense.setSplitBetween(splitBetween);

        Expense createdExpense = expenseService.createExpense(tripId, expense, currentUser);
        ExpenseDTO dto = DtoMapper.toExpenseDTO(createdExpense);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Expense created successfully", dto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getAllExpenses(
            @PathVariable Long tripId,
            @CurrentUser User currentUser) {

        List<Expense> expenses = expenseService.getAllExpensesForTrip(tripId, currentUser);
        List<ExpenseDTO> dtos = expenses.stream()
                .map(DtoMapper::toExpenseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ExpenseSummaryDTO>> getExpenseSummary(
            @PathVariable Long tripId,
            @CurrentUser User currentUser) {

        Map<String, Map<String, BigDecimal>> detailedSummary =
                expenseService.getDetailedExpenseSummary(tripId, currentUser);

        BigDecimal totalExpenses = expenseService.getTotalExpensesForTrip(tripId, currentUser);

        ExpenseSummaryDTO summary = new ExpenseSummaryDTO();
        summary.setTotalExpenses(totalExpenses);

        detailedSummary.forEach((email, amounts) -> {
            ExpenseSummaryDTO.UserExpenseSummary userSummary =
                    new ExpenseSummaryDTO.UserExpenseSummary();
            userSummary.setEmail(email);
            userSummary.setPaid(amounts.get("paid"));
            userSummary.setOwes(amounts.get("owes"));
            userSummary.setBalance(amounts.get("balance"));

            summary.getUserSummaries().put(email, userSummary);
        });

        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> updateExpense(
            @PathVariable Long tripId,
            @PathVariable Long expenseId,
            @Valid @RequestBody UpdateExpenseRequest request,
            @CurrentUser User currentUser) {

        Expense updatedExpense = new Expense();
        updatedExpense.setDescription(request.getDescription());
        updatedExpense.setAmount(request.getAmount());

        if (request.getPaidByUserId() != null) {
            User paidBy = userService.findById(request.getPaidByUserId());
            updatedExpense.setPaidBy(paidBy);
        }

        if (request.getSplitBetweenUserIds() != null && !request.getSplitBetweenUserIds().isEmpty()) {
            Set<User> splitBetween = request.getSplitBetweenUserIds().stream()
                    .map(userService::findById)
                    .collect(Collectors.toSet());
            updatedExpense.setSplitBetween(splitBetween);
        }
            Expense resExpense = expenseService.updateExpense(expenseId, updatedExpense, currentUser);
            ExpenseDTO dto = DtoMapper.toExpenseDTO(resExpense);
//            Expense expense = expenseService.updateExpense(expenseId, updatedExpense, currentUser);
//            ExpenseDTO dto = DtoMapper.toExpenseDTO(expense);

            return ResponseEntity.ok(ApiResponse.success("Expense updated successfully", dto));

    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(
            @PathVariable Long tripId,
            @PathVariable Long expenseId,
            @CurrentUser User currentUser) {
        // return ResponseEntity.ok(ApiResponse.success(null));
        expenseService.deleteExpense(expenseId, currentUser);

        return ResponseEntity.ok(ApiResponse.success("Expense deleted successfully", null));
    }

}
