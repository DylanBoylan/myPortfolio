package abc_app;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; //assertEquals, assertTrue, assertFalse,

class BookingTest {

    private Booking booking; //instance variable booking of type Booking

    @BeforeEach //should be executed before each test
    void setUp() {
        booking = new Booking("B1", "2024-08-01", "2024-08-07");  //Creates a new Booking object
    }

    @Test
    void testConstructor() { //checking correctly initialized
        assertEquals("B1", booking.getId());
        assertEquals("2024-08-01", booking.getStartDate());
        assertEquals("2024-08-07", booking.getEndDate());
        assertFalse(booking.isConfirmed()); // Confirmed should be false by default
    }

    @Test
    void testGetStartDate() { //checks if returns the correct start date.
        assertEquals("2024-08-01", booking.getStartDate());
    }

    @Test
    void testGetEndDate() { //checks if returns the correct end date.
        assertEquals("2024-08-07", booking.getEndDate());
    }

    @Test
    void testIsConfirmed() {
        assertFalse(booking.isConfirmed()); // Initially false
        
        // Confirm the booking
        booking.setConfirmed();
        assertTrue(booking.isConfirmed()); // After setting confirmed, should be true
    }

    @Test
    void testSetConfirmed() { // tests if calling setConfirmed() updates the confirmed status correctly
        // Initially should be false
        assertFalse(booking.isConfirmed());
        
        // Call setConfirmed method
        booking.setConfirmed();
        // After calling setConfirmed, should be true
        assertTrue(booking.isConfirmed());
    }
    
    @Test
    void testToString() { // Test the toString method
        String expected = "Booking [id=B1, startDate=2024-08-01, endDate=2024-08-07, confirmed=false]";
        assertEquals(expected, booking.toString());

        // Confirm the booking and test again
        booking.setConfirmed();
        expected = "Booking [id=B1, startDate=2024-08-01, endDate=2024-08-07, confirmed=true]";
        assertEquals(expected, booking.toString());
    }
}
