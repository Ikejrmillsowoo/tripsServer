package planner.demo.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TripsTest {
    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Trips u = new Trips();
        assertNull(u.getId());
        assertNull(u.getLocation_id());
        assertNull(u.getExpense_id());
        assertNull(u.getUser_id());
        assertNull(u.getStartDate());
        assertNull(u.getEndDate());

        u.setId(42L);
        u.setStartDate(LocalDate.of(2000, 1, 1));
        u.setEndDate(LocalDate.of(2020,12,31));
        u.setUser_id(11L);
        u.setExpense_id(7L);
        u.setLocation_id(7L);

        assertEquals(42L, u.getId());
        assertEquals(LocalDate.of(2000, 1, 1), u.getStartDate());
        assertEquals(LocalDate.of(2020,12,31), u.getEndDate());
        assertEquals(11L, u.getUser_id());
        assertEquals(7L, u.getExpense_id());
        assertEquals(7L, u.getLocation_id());
    }

    @Test
    void testAllArgsConstructorAndImmutabilityOfId() {
        Trips u = new Trips("Trips to paris", 2L,3L,4L, LocalDate.of(2020,1,1), LocalDate.of(2020,12,31));
        // id should be null until persisted
        assertNull(u.getId());
        assertEquals("Trips to paris", u.getTripName());
        assertEquals(2L, u.getLocation_id());
        assertEquals(3L, u.getUser_id());
        assertEquals(4L, u.getExpense_id());
        assertEquals(LocalDate.of(2020,1,1), u.getStartDate());
        assertEquals(LocalDate.of(2020,12,31), u.getEndDate());

        // change values
        u.setTripName("Trips to london");
        u.setLocation_id(5L);
        u.setUser_id(6L);
        u.setExpense_id(8L);
        u.setStartDate(LocalDate.of(2021,1,1));
        u.setEndDate(LocalDate.of(2021,12,31));

        assertEquals("Trips to london", u.getTripName());
        assertEquals(5L, u.getLocation_id());
        assertEquals(6L, u.getUser_id());
        assertEquals(8L, u.getExpense_id());
        assertEquals(LocalDate.of(2021,1,1), u.getStartDate());
        assertEquals(LocalDate.of(2021,12,31), u.getEndDate());

    }

    @Test
    void testEqualsBehaviorSimple() {
        Trips a = new Trips("Trips to paris", 2L,3L,4L, LocalDate.of(2020,1,1), LocalDate.of(2020,12,31));
        Trips b = new Trips("Trips to paris", 2L,3L,4L, LocalDate.of(2020,1,1), LocalDate.of(2020,12,31));
        // default equals from Object: different instances are not equal
        assertNotEquals(a, b);
        // same instance equals itself (reference equality)
        assertSame(a, a);
    }
}
