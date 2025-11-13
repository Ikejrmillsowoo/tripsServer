package planner.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
}