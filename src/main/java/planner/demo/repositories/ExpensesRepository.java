package planner.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Expense;
@Repository
public interface ExpensesRepository extends CrudRepository<Expense, Long> {
}
