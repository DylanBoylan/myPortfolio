package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class AuthorizePayTest {

	private AuthorizePayment authorizePay; //instance variable booking of type Booking

    @BeforeEach //should be executed before each test
    void setUp() {
    	authorizePay = new AuthorizePayment();
    }

    @Test
    void testConstructor() { //checking correctly initialized
    	authorizePay.setProduct("Laptop");
    	authorizePay.setTotal(1);
        assertEquals(1, authorizePay.getTotal());
    }

    
}
