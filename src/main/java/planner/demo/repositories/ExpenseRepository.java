package planner.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planner.demo.models.Expense;
import planner.demo.models.Trip;
import planner.demo.models.User;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    // Find all expenses for a trip
    List<Expense> findByTrip(Trip trip);

    // Find expenses by trip ID
    List<Expense> findByTripId(Long tripId);

    // Find expenses paid by a specific user
    List<Expense> findByPaidBy(User user);

    // Find expenses where a user is involved (either paid or splitting)
    @Query("SELECT DISTINCT e FROM Expense e " +
            "LEFT JOIN e.splitBetween s " +
            "WHERE e.paidBy = :user OR s = :user")
    List<Expense> findExpensesByUser(@Param("user") User user);

    // Calculate total expenses for a trip
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.trip = :trip")
    BigDecimal calculateTotalExpensesForTrip(@Param("trip") Trip trip);

    // Calculate total paid by a user for a trip
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e " +
            "WHERE e.trip = :trip AND e.paidBy = :user")
    BigDecimal calculateTotalPaidByUser(
            @Param("trip") Trip trip,
            @Param("user") User user
    );

    // Calculate total owed by a user for a trip
    @Query("SELECT COALESCE(SUM(e.amount / SIZE(e.splitBetween)), 0) " +
            "FROM Expense e JOIN e.splitBetween s " +
            "WHERE e.trip = :trip AND s = :user")
    BigDecimal calculateTotalOwedByUser(
            @Param("trip") Trip trip,
            @Param("user") User user
    );

    // Find expenses split among specific users
    @Query("SELECT e FROM Expense e JOIN e.splitBetween s " +
            "WHERE e.trip = :trip AND s = :user")
    List<Expense> findExpensesSplitWithUser(
            @Param("trip") Trip trip,
            @Param("user") User user
    );

    // Count expenses for a trip
    long countByTrip(Trip trip);

    // Delete all expenses for a trip
    void deleteByTrip(Trip trip);

}
