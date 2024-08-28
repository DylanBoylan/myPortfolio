package abc_app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;

public class BookingBeanTest {

    private BookingBean bookingBean;

    @BeforeEach
    void setUp() {
        bookingBean = new BookingBean();
      
    }

    @Test
    void testIsAccessible() {
        // Test for accessible parking spaces
        assertTrue(bookingBean.isAccessible(1), "Space 1 should be accessible");
        assertTrue(bookingBean.isAccessible(5), "Space 5 should be accessible");
        assertTrue(bookingBean.isAccessible(6), "Space 6 should be accessible");
        assertTrue(bookingBean.isAccessible(10), "Space 10 should be accessible");

        // Test for non-accessible parking spaces
        assertFalse(bookingBean.isAccessible(2), "Space 2 should not be accessible");
        assertFalse(bookingBean.isAccessible(4), "Space 4 should not be accessible");
        assertFalse(bookingBean.isAccessible(7), "Space 7 should not be accessible");
    }
    @Test
    void testSetAndGetUsername() {
        String testUsername = "testUser";
        
        // Set the username
        bookingBean.setUsername(testUsername);
        
        // Get the username and verify
        assertEquals(testUsername, bookingBean.getUsername(), "Username should be set and retrieved correctly");
    }
    @Test
    void testSetAndGetFilteredSpaces() {
        List<ParkingSpace> testSpaces = new ArrayList<>();
        testSpaces.add(new ParkingSpace("A1", true, null)); // Example space
        
        // Set the filteredSpaces
        bookingBean.setFilteredSpaces(testSpaces);
        
        // Get the filteredSpaces and verify
        assertEquals(testSpaces, bookingBean.getFilteredSpaces(), "Filtered spaces should be set and retrieved correctly");
    }
    @Test
    void testSetAndGetSelectedSpace() {
        ParkingSpace testSpace = new ParkingSpace("A1", true, null);
        
        // Set the selectedSpace
        bookingBean.setSelectedSpace(testSpace);
        
        // Get the selectedSpace and verify
        assertEquals(testSpace, bookingBean.getSelectedSpace(), "Selected space should be set and retrieved correctly");
    }

}
