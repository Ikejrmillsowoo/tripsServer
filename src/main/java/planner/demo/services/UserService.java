package planner.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import planner.demo.models.User;
import planner.demo.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;

    public User createUser(User user) {
       userRepo.save(user);
       return user;
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    public User updateUser(Long id, User userDetails) {
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setTrip_id(userDetails.getTrip_id());
            userRepo.save(user);
        }
        return user;
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
