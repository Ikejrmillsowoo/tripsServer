package planner.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planner.demo.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find users by email (for inviting collaborators)
    // This can be used to search for users when adding them to trips
    Optional<User> findByEmailIgnoreCase(String email);
}