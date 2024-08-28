package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PayPalTest {

	private OrderDetail orderDetail; //instance variable booking of type Booking

    @BeforeEach //should be executed before each test
    void setUp() {
    	orderDetail = new OrderDetail("B1", 1, 2, 3, 4);  //Creates a new Booking object
    }

    @Test
    void testConstructor() { //checking correctly initialized
        assertEquals("B1", orderDetail.getProductName());
        assertEquals("1.00", orderDetail.getSubtotal());
        assertEquals("2.00", orderDetail.getShipping());
        assertEquals("3.00", orderDetail.getTax());
        assertEquals("4.00", orderDetail.getTotal());
    }

    
}
