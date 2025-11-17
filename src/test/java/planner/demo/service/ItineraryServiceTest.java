//// ...existing code...
//package planner.demo.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import planner.demo.models.Itinerary;
//import planner.demo.repositories.ItineraryRepository;
//import planner.demo.services.ItineraryService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class ItineraryServiceTest {
//
//    @Mock
//    private ItineraryRepository itineraryRepo;
//
//    @InjectMocks
//    private ItineraryService itineraryService;
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
//    void createItinerary_savesAndReturns() {
//        Itinerary it = new Itinerary(null, "Sightseeing", "09:00", "12:00", "Center", 50.0);
//        when(itineraryRepo.save(it)).thenReturn(it);
//
//        Itinerary res = itineraryService.createItinerary(it);
//
//        verify(itineraryRepo).save(it);
//        assertSame(it, res);
//    }
//
//    @Test
//    void getAllItineraries_returnsList() {
//        Itinerary i1 = new Itinerary(null, "A", "08:00", "09:00", "Loc1", 10.0);
//        Itinerary i2 = new Itinerary(null, "B", "10:00", "11:00", "Loc2", 20.0);
//        when(itineraryRepo.findAll()).thenReturn(Arrays.asList(i1, i2));
//
//        List<Itinerary> all = itineraryService.getAllItineraries();
//
//        assertEquals(2, all.size());
//        assertSame(i1, all.get(0));
//        assertSame(i2, all.get(1));
//    }
//
//    @Test
//    void getItineraryById_found() {
//        Itinerary it = new Itinerary(7L, "Trip", "07:00", "08:00", "L", 15.0);
//        when(itineraryRepo.findById(7L)).thenReturn(Optional.of(it));
//
//        Itinerary res = itineraryService.getItineraryById(7L);
//        assertSame(it, res);
//    }
//
//    @Test
//    void getItineraryById_notFound() {
//        when(itineraryRepo.findById(99L)).thenReturn(Optional.empty());
//        assertNull(itineraryService.getItineraryById(99L));
//    }
//
//    @Test
//    void updateItinerary_existing_updatesAndReturns() {
//        Itinerary existing = new Itinerary(5L, "Old", "06:00", "07:00", "Loc", 5.0);
//        Itinerary updated = new Itinerary(null, "New", "09:00", "10:00", "LocNew", 25.0);
//
//        when(itineraryRepo.findById(5L)).thenReturn(Optional.of(existing));
//        when(itineraryRepo.save(existing)).thenReturn(existing);
//
//        Itinerary res = itineraryService.updateItinerary(5L, updated);
//
//        verify(itineraryRepo).save(existing);
//        assertEquals("New", res.getActivities());
//        assertEquals("09:00", res.getStartTime());
//        assertEquals("10:00", res.getEndTime());
//        assertEquals("LocNew", res.getLocation());
//        assertEquals(25.0, res.getTotalCost());
//    }
//
//    @Test
//    void updateItinerary_notFound_returnsNull() {
//        Itinerary updated = new Itinerary(null, "X", "00:00", "01:00", "L", 1.0);
//        when(itineraryRepo.findById(100L)).thenReturn(Optional.empty());
//
//        Itinerary res = itineraryService.updateItinerary(100L, updated);
//
//        assertNull(res);
//        verify(itineraryRepo, never()).save(any());
//    }
//
//    @Test
//    void deleteItinerary_callsRepo() {
//        doNothing().when(itineraryRepo).deleteById(3L);
//
//        itineraryService.deleteItinerary(3L);
//
//        verify(itineraryRepo).deleteById(3L);
//    }
//}
//// ...existing code...
