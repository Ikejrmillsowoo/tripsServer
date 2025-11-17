//package planner.demo.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import planner.demo.models.User;
//import planner.demo.repositories.UserRepository;
//import planner.demo.services.UserService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepo;
//
//    @InjectMocks
//    private UserService userService;
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
//    void createUser_savesAndReturnsUser() {
//        User u = new User("Alice", "Smith", "alice@example.com", 1L);
//        when(userRepo.save(u)).thenReturn(u);
//
//        User result = userService.createUser(u);
//
//        verify(userRepo).save(u);
//        assertSame(u, result);
//    }
//
//    @Test
//    void getUserById_found() {
//        User u = new User("Bob", "Jones", "bob@example.com", 2L);
//        u.setId(10L);
//        when(userRepo.findById(10L)).thenReturn(Optional.of(u));
//
//        User result = userService.getUserById(10L);
//
//        assertSame(u, result);
//    }
//
//    @Test
//    void getUserById_notFound() {
//        when(userRepo.findById(99L)).thenReturn(Optional.empty());
//        assertNull(userService.getUserById(99L));
//    }
//
//    @Test
//    void getAllUsers_returnsList() {
//        User u1 = new User("A","B","a@b.com", 1L);
//        User u2 = new User("C","D","c@d.com", 2L);
//        when(userRepo.findAll()).thenReturn(Arrays.asList(u1, u2));
//
//        List<User> all = userService.getAllUsers();
//
//        assertEquals(2, all.size());
//        assertSame(u1, all.get(0));
//        assertSame(u2, all.get(1));
//    }
//
//    @Test
//    void updateUser_existing_updatesAndReturns() {
//        User existing = new User("Old", "Name", "old@ex.com", 3L);
//        existing.setId(5L);
//        User details = new User("New", "Name", "new@ex.com", 9L);
//
//        when(userRepo.findById(5L)).thenReturn(Optional.of(existing));
//        when(userRepo.save(existing)).thenReturn(existing);
//
//        User res = userService.updateUser(5L, details);
//
//        verify(userRepo).save(existing);
//        assertEquals("New", res.getFirstName());
//        assertEquals("Name", res.getLastName());
//        assertEquals("new@ex.com", res.getEmail());
//        assertEquals(9L, res.getTrip_id());
//    }
//
//    @Test
//    void updateUser_notFound_returnsNullAndDoesNotSave() {
//        User details = new User("X","Y","z@ex.com", 1L);
//        when(userRepo.findById(100L)).thenReturn(Optional.empty());
//
//        User res = userService.updateUser(100L, details);
//
//        assertNull(res);
//        verify(userRepo, never()).save(any());
//    }
//
//    @Test
//    void deleteUser_callsRepository() {
//        doNothing().when(userRepo).deleteById(7L);
//
//        userService.deleteUser(7L);
//
//        verify(userRepo).deleteById(7L);
//    }
//}
