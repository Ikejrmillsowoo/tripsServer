//package planner.demo.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import planner.demo.models.Trip;
//import planner.demo.repositories.TripRepository;
//import planner.demo.services.TripService;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class TripServiceTest {
//
//    @Mock
//    private TripRepository tripRepo;
//
//    @InjectMocks
//    private TripService tripService;
//
//    private AutoCloseable mocks;
//
//    @BeforeEach
//    void setUp() {
//        mocks = MockitoAnnotations.openMocks(this);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        if (mocks != null) mocks.close();
//    }
//
//    @Test
//    void createTrip_savesAndReturns() {
//        Trip t = new Trip("Holiday", 1L, 2L, 3L, LocalDate.of(2020,1,1), LocalDate.of(2020,1,10));
//        when(tripRepo.save(t)).thenReturn(t);
//
//        Trip res = tripService.createTrip(t);
//
//        verify(tripRepo).save(t);
//        assertSame(t, res);
//    }
//
//    @Test
//    void getAllTrips_returnsList() {
//        Trip t1 = new Trip("A",1L,2L,3L, LocalDate.of(2021,1,1), LocalDate.of(2021,1,2));
//        Trip t2 = new Trip("B",4L,5L,6L, LocalDate.of(2022,2,2), LocalDate.of(2022,2,3));
//        when(tripRepo.findAll()).thenReturn(Arrays.asList(t1, t2));
//
//        List<Trip> all = tripService.getAllTrips();
//
//        assertEquals(2, all.size());
//        assertSame(t1, all.get(0));
//        assertSame(t2, all.get(1));
//    }
//
//    @Test
//    void getTripById_found() {
//        Trip t = new Trip("Trip",1L,2L,3L, LocalDate.of(2023,3,3), LocalDate.of(2023,3,4));
//        t.setId(8L);
//        when(tripRepo.findById(8L)).thenReturn(Optional.of(t));
//
//        Trip res = tripService.getTripById(8L);
//        assertSame(t, res);
//    }
//
//    @Test
//    void getTripById_notFound() {
//        when(tripRepo.findById(99L)).thenReturn(Optional.empty());
//        assertNull(tripService.getTripById(99L));
//    }
//
//    @Test
//    void updateTrip_existing_updatesAndReturns() {
//        Trip existing = new Trip("Old",1L,2L,3L, LocalDate.of(2020,1,1), LocalDate.of(2020,1,2));
//        existing.setId(5L);
//        Trip updated = new Trip("New",9L,8L,7L, LocalDate.of(2021,5,5), LocalDate.of(2021,5,9));
//
//        when(tripRepo.findById(5L)).thenReturn(Optional.of(existing));
//        when(tripRepo.save(existing)).thenReturn(existing);
//
//        Trip res = tripService.updateTrip(5L, updated);
//
//        verify(tripRepo).save(existing);
//        assertEquals("New", res.getTripName());
//        assertEquals(LocalDate.of(2021,5,5), res.getStartDate());
//        assertEquals(LocalDate.of(2021,5,9), res.getEndDate());
//        assertEquals(9L, res.getLocationId());
//        assertEquals(8L, res.getUserId());
//        assertEquals(7L, res.getExpenseId());
//    }
//
//    @Test
//    void updateTrip_notFound_returnsNull() {
//        Trip updated = new Trip("X",1L,2L,3L, LocalDate.of(2020,1,1), LocalDate.of(2020,1,2));
//        when(tripRepo.findById(100L)).thenReturn(Optional.empty());
//
//        Trip res = tripService.updateTrip(100L, updated);
//
//        assertNull(res);
//        verify(tripRepo, never()).save(any());
//    }
//
//    @Test
//    void deleteTrip_callsRepo() {
//        doNothing().when(tripRepo).deleteById(4L);
//
//        tripService.deleteTrip(4L);
//
//        verify(tripRepo).deleteById(4L);
//    }
//}
