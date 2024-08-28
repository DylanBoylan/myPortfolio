package abc_app;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; //assertEquals, assertTrue, assertFalse,

class ParkingSpaceTest {

    private ParkingSpace parkingSpace; // instance variable parkingSpace of type ParkingSpace

    @BeforeEach //should run before each individual test
    void setUp() { // each test starts fresh and independent
        parkingSpace = new ParkingSpace("P1", true, "ABC Corp"); //"P1" for id,"true" for accessible flag,"ABC Corp"for company.
    }

    @Test
    void testConstructor() { //checking correctly initialized
        assertEquals("P1", parkingSpace.getId()); 
        assertTrue(parkingSpace.isAccessible());
        assertTrue(parkingSpace.isAvailable());
        assertEquals("ABC Corp", parkingSpace.getCompany());
        assertFalse(parkingSpace.isStatus());
        assertEquals(30.0, parkingSpace.getPrice());
        assertNull(parkingSpace.getUserName());
    }

    @Test
    void testSetAvailable() { //tests both possible states (true and false) of the availability.
        parkingSpace.setAvailable(false);
        assertFalse(parkingSpace.isAvailable());

        parkingSpace.setAvailable(true);
        assertTrue(parkingSpace.isAvailable());
    }

    @Test
    void testSetCompany() { // checks if correctly updates company 
        parkingSpace.setCompany("XYZ Inc");
        assertEquals("XYZ Inc", parkingSpace.getCompany());
    }

    @Test
    void testSetStatus() { //checks if correctly updates status 
        parkingSpace.setStatus(true);
        assertTrue(parkingSpace.isStatus());

        parkingSpace.setStatus(false);
        assertFalse(parkingSpace.isStatus());
    }

    @Test
    void testSetPrice() { //checks if correctly updates price 
        parkingSpace.setPrice(15.0);
        assertEquals(15.0, parkingSpace.getPrice());

        parkingSpace.setPrice(20.5);
        assertEquals(20.5, parkingSpace.getPrice());
    }

    @Test
    void testSetUserName() { //checks if correctly updates userName 
        parkingSpace.setUserName("john_doe");
        assertEquals("john_doe", parkingSpace.getUserName());

        parkingSpace.setUserName("jane_smith");
        assertEquals("jane_smith", parkingSpace.getUserName());
    }
}
