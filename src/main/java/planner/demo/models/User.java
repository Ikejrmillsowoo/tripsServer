package planner.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long trip_id;

    // added no-args constructor required by JPA
    public User() {
    }

    public User(String firstName, String lastName, String email, Long trip_id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.trip_id = trip_id; // fixed: set the trip_id
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTrip_id() {
        return trip_id;
    }
    public void setTrip_id(Long trip_id) {
        this.trip_id = trip_id;
    }
}
