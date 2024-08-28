package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateBookingTest {

    private BookingBean bookingBean;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() {
        // Initialize the BookingBean
        bookingBean = new BookingBean();
        bookingBean.init(); // Initialize the bean

        // Set up date format to match the format used in Booking
        dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");

        // Set up some default dates for testing
        bookingBean.setStartDate(new Date());
        bookingBean.setEndDate(new Date(System.currentTimeMillis() + 86400000L)); // 1 day later

        // Set a test user as logged in
        bookingBean.setUsername("testUser");
    }

    @Test
    public void testUpdateBookingSuccess() throws Exception {
        // Set up the test data
        String loggedInUsername = "testUser";

        // Create a parking space and associate it with the logged-in user
        ParkingSpace space = new ParkingSpace("A1", true, null);
        space.setUserName(loggedInUsername);
        space.setAvailable(true);

        bookingBean.getParkingSpaces().add(space);

        // Create a booking associated with the space using the required date format
        String startDate = dateFormat.format(dateFormat.parse("Tuesday, August 20, 2024"));
        String endDate = dateFormat.format(dateFormat.parse("Saturday, August 24, 2024"));
        Booking booking = new Booking("A1", startDate, endDate);
        bookingBean.getBookings().add(booking);

        // Select the space for updating
        bookingBean.setSelectedSpace(space);
        bookingBean.setselectedID("A1");

        // Call the method under test
        String result = bookingBean.updateBooking();

        // Verify the expected outcome
        assertEquals("updateConfirmation.xhtml", result);
        assertFalse(space.isAvailable());
        assertEquals(loggedInUsername, space.getUserName());

        // Verify the booking was updated correctly
        assertTrue(bookingBean.getBookings().contains(booking));
    }

    @Test
    public void testUpdateBookingNoSpaceSelected() {
        // Ensure no space is selected
        bookingBean.setSelectedSpace(null);

        // Call the method under test
        String result = bookingBean.updateBooking();
        System.out.println(result);

        // Verify the expected outcome
        assertNull(result);
    }

    @Test
    public void testUpdateBookingSpaceUnavailable() throws Exception {
        // Set up the test data
        String loggedInUsername = "testUser";

        // Create a parking space that is unavailable
        ParkingSpace space = new ParkingSpace("A1", true, null);
        space.setUserName("otherUser"); // Make the space belong to someone else
        space.setAvailable(false);

        bookingBean.getParkingSpaces().add(space);

        // Attempt to update this space
        bookingBean.setselectedID("A1");
        bookingBean.setSelectedSpace(space);

        // Call the method under test
        String result = bookingBean.updateBooking();

        // Verify the expected outcome
        assertNull(result);
        assertFalse(space.isAvailable());  // Space should remain unavailable
    }
}
