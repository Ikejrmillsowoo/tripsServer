package planner.demo.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Users u = new Users();
        assertNull(u.getId());
        assertNull(u.getFirstName());
        assertNull(u.getLastName());
        assertNull(u.getEmail());
        assertNull(u.getTrip_id());

        u.setId(42L);
        u.setFirstName("Alice");
        u.setLastName("Smith");
        u.setEmail("alice@example.com");
        u.setTrip_id(7L);

        assertEquals(42L, u.getId());
        assertEquals("Alice", u.getFirstName());
        assertEquals("Smith", u.getLastName());
        assertEquals("alice@example.com", u.getEmail());
        assertEquals(7L, u.getTrip_id());
    }

    @Test
    void testAllArgsConstructorAndImmutabilityOfId() {
        Users u = new Users("Bob", "Jones", "bob@example.com", 99L);
        // id should be null until persisted
        assertNull(u.getId());
        assertEquals("Bob", u.getFirstName());
        assertEquals("Jones", u.getLastName());
        assertEquals("bob@example.com", u.getEmail());
        assertEquals(99L, u.getTrip_id());

        // change values
        u.setFirstName("Bobby");
        u.setLastName("Johnson");
        u.setEmail("bobby@example.org");
        u.setTrip_id(100L);

        assertEquals("Bobby", u.getFirstName());
        assertEquals("Johnson", u.getLastName());
        assertEquals("bobby@example.org", u.getEmail());
        assertEquals(100L, u.getTrip_id());
    }

    @Test
    void testEqualsBehaviorSimple() {
        Users a = new Users("Sam", "Lee", "sam@example.com", 1L);
        Users b = new Users("Sam", "Lee", "sam@example.com", 1L);
        // default equals from Object: different instances are not equal
        assertNotEquals(a, b);
        // same instance equals itself (reference equality)
        assertSame(a, a);
    }
}
