package planner.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Expenses;
@Repository
public interface ExpensesRepository extends CrudRepository<Expenses, Long> {
}
