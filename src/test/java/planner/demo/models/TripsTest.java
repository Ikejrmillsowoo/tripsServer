package planner.demo.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TripsTest {
    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Trip u = new Trip();
        assertNull(u.getId());
        assertNull(u.getLocationId());
        assertNull(u.getExpenseId());
        assertNull(u.getUserId());
        assertNull(u.getStartDate());
        assertNull(u.getEndDate());

        u.setId(42L);
        u.setStartDate(LocalDate.of(2000, 1, 1));
        u.setEndDate(LocalDate.of(2020,12,31));
        u.setUserId(11L);
        u.setExpenseId(7L);
        u.setLocationId(7L);

        assertEquals(42L, u.getId());
        assertEquals(LocalDate.of(2000, 1, 1), u.getStartDate());
        assertEquals(LocalDate.of(2020,12,31), u.getEndDate());
        assertEquals(11L, u.getUserId());
        assertEquals(7L, u.getExpenseId());
        assertEquals(7L, u.getLocationId());
    }

    @Test
    void testAllArgsConstructorAndImmutabilityOfId() {
        Trip u = new Trip("Trips to paris", 2L,3L,4L, LocalDate.of(2020,1,1), LocalDate.of(2020,12,31));
        // id should be null until persisted
        assertNull(u.getId());
        assertEquals("Trips to paris", u.getTripName());
        assertEquals(2L, u.getLocationId());
        assertEquals(3L, u.getUserId());
        assertEquals(4L, u.getExpenseId());
        assertEquals(LocalDate.of(2020,1,1), u.getStartDate());
        assertEquals(LocalDate.of(2020,12,31), u.getEndDate());

        // change values
        u.setTripName("Trips to london");
        u.setLocationId(5L);
        u.setUserId(6L);
        u.setExpenseId(8L);
        u.setStartDate(LocalDate.of(2021,1,1));
        u.setEndDate(LocalDate.of(2021,12,31));

        assertEquals("Trips to london", u.getTripName());
        assertEquals(5L, u.getLocationId());
        assertEquals(6L, u.getUserId());
        assertEquals(8L, u.getExpenseId());
        assertEquals(LocalDate.of(2021,1,1), u.getStartDate());
        assertEquals(LocalDate.of(2021,12,31), u.getEndDate());

    }

    @Test
    void testEqualsBehaviorSimple() {
        Trip a = new Trip("Trips to paris", 2L,3L,4L, LocalDate.of(2020,1,1), LocalDate.of(2020,12,31));
        Trip b = new Trip("Trips to paris", 2L,3L,4L, LocalDate.of(2020,1,1), LocalDate.of(2020,12,31));
        // default equals from Object: different instances are not equal
        assertNotEquals(a, b);
        // same instance equals itself (reference equality)
        assertSame(a, a);
    }
}
