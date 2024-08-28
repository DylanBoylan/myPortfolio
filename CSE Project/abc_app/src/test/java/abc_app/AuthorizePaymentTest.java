package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorizePaymentTest {

    private AuthorizePayment authorizePayment;

    @BeforeEach
    public void setUp() {
        // Initialize the AuthorizePayment object
        authorizePayment = new AuthorizePayment();
    }

    @Test
    public void testGetAndSetProduct() {
        // Test setting and getting the product name
        authorizePayment.setProduct("Test Product");
        assertEquals("Test Product", authorizePayment.getProduct());
    }

    @Test
    public void testGetAndSetTotal() {
        // Test setting and getting the total value
        authorizePayment.setTotal(100.0f);
        assertEquals(100.0f, authorizePayment.getTotal());
    }

    @Test
    public void testCheckOutNoExceptions() {
        // This test verifies that checkOut does not throw any unexpected exceptions
        // even if the PayPal service interaction fails (simulated here without PayPalRESTException).
        
        // Set up the data
        authorizePayment.setProduct("Test Product");
        authorizePayment.setTotal(100.0f);

        // Call the method under test
        try {
            String result = authorizePayment.checkOut();
            assertNull(result, "checkOut should return null");
        } catch (Exception e) {
            fail("checkOut should not throw any exception: " + e.getMessage());
        }
    }

    @Test
    public void testCheckOutWithNoProduct() {
        // This test checks if the checkOut method behaves correctly when no product is set
        try {
            authorizePayment.setProduct(null);
            String result = authorizePayment.checkOut();
            assertNull(result, "checkOut should return null when product is null");
        } catch (Exception e) {
            fail("checkOut should not throw any exception when product is null: " + e.getMessage());
        }
    }
}
