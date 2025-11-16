package planner.demo.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import planner.demo.models.Expense;
import planner.demo.models.Trip;
import planner.demo.models.User;
import planner.demo.repositories.ExpenseRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TripService tripService;
    private final UserService userService;

    public Expense createExpense(Long tripId, Expense expense, User user) {
        Trip trip = tripService.getTripById(tripId, user);

        // Validate amount
        if (expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        // Validate split between users
        if (expense.getSplitBetween() == null || expense.getSplitBetween().isEmpty()) {
            throw new RuntimeException("Expense must be split between at least one person");
        }

        // Validate all users in splitBetween are trip collaborators
        for (User splitUser : expense.getSplitBetween()) {
            if (!trip.getCollaborators().contains(splitUser)) {
                throw new RuntimeException("Can only split expenses with trip collaborators");
            }
        }

        // Validate paidBy is a trip collaborator
        if (!trip.getCollaborators().contains(expense.getPaidBy())) {
            throw new RuntimeException("Payer must be a trip collaborator");
        }

        expense.setTrip(trip);
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpensesForTrip(Long tripId, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        return expenseRepository.findByTrip(trip);
    }

    public Expense getExpenseById(Long expenseId, User user) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Verify user has access to the trip
        tripService.getTripById(expense.getTrip().getId(), user);

        return expense;
    }

    public Expense updateExpense(Long expenseId, Expense updatedExpense, User user) {
        Expense expense = getExpenseById(expenseId, user);
        Trip trip = expense.getTrip();

        if (updatedExpense.getDescription() != null) {
            expense.setDescription(updatedExpense.getDescription());
        }
        if (updatedExpense.getAmount() != null) {
            if (updatedExpense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Amount must be greater than zero");
            }
            expense.setAmount(updatedExpense.getAmount());
        }
        if (updatedExpense.getPaidBy() != null) {
            if (!trip.getCollaborators().contains(updatedExpense.getPaidBy())) {
                throw new RuntimeException("Payer must be a trip collaborator");
            }
            expense.setPaidBy(updatedExpense.getPaidBy());
        }
        if (updatedExpense.getSplitBetween() != null && !updatedExpense.getSplitBetween().isEmpty()) {
            for (User splitUser : updatedExpense.getSplitBetween()) {
                if (!trip.getCollaborators().contains(splitUser)) {
                    throw new RuntimeException("Can only split expenses with trip collaborators");
                }
            }
            expense.setSplitBetween(updatedExpense.getSplitBetween());
        }

        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long expenseId, User user) {
        Expense expense = getExpenseById(expenseId, user);
        expenseRepository.delete(expense);
    }

    public BigDecimal getTotalExpensesForTrip(Long tripId, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        return expenseRepository.calculateTotalExpensesForTrip(trip);
    }

    public Map<String, BigDecimal> getExpenseSummaryForTrip(Long tripId, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        List<Expense> expenses = expenseRepository.findByTrip(trip);

        Map<String, BigDecimal> summary = new HashMap<>();

        // Initialize for all collaborators
        for (User collaborator : trip.getCollaborators()) {
            summary.put(collaborator.getEmail(), BigDecimal.ZERO);
        }

        // Calculate balance for each user (paid - owed)
        for (User collaborator : trip.getCollaborators()) {
            BigDecimal paid = expenseRepository.calculateTotalPaidByUser(trip, collaborator);
            BigDecimal owed = BigDecimal.ZERO;

            // Calculate what they owe
            for (Expense expense : expenses) {
                if (expense.getSplitBetween().contains(collaborator)) {
                    BigDecimal share = expense.getAmount()
                            .divide(BigDecimal.valueOf(expense.getSplitBetween().size()), 2, RoundingMode.HALF_UP);
                    owed = owed.add(share);
                }
            }

            BigDecimal balance = paid.subtract(owed);
            summary.put(collaborator.getEmail(), balance);
        }

        return summary;
    }

    public Map<String, Map<String, BigDecimal>> getDetailedExpenseSummary(Long tripId, User user) {
        Trip trip = tripService.getTripById(tripId, user);
        List<Expense> expenses = expenseRepository.findByTrip(trip);

        Map<String, Map<String, BigDecimal>> detailedSummary = new HashMap<>();

        for (User collaborator : trip.getCollaborators()) {
            Map<String, BigDecimal> userSummary = new HashMap<>();

            BigDecimal paid = expenseRepository.calculateTotalPaidByUser(trip, collaborator);
            BigDecimal owed = BigDecimal.ZERO;

            for (Expense expense : expenses) {
                if (expense.getSplitBetween().contains(collaborator)) {
                    BigDecimal share = expense.getAmount()
                            .divide(BigDecimal.valueOf(expense.getSplitBetween().size()), 2, RoundingMode.HALF_UP);
                    owed = owed.add(share);
                }
            }

            userSummary.put("paid", paid);
            userSummary.put("owes", owed);
            userSummary.put("balance", paid.subtract(owed));

            detailedSummary.put(collaborator.getEmail(), userSummary);
        }

        return detailedSummary;
    }
}
