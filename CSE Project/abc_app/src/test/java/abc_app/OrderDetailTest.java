package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderDetailTest {

    private OrderDetail orderDetail;

    @BeforeEach
    public void setUp() {
        // Initialize OrderDetail object with some test data
        orderDetail = new OrderDetail("Test Product", 100.00f, 10.00f, 15.00f, 125.00f);
    }

    @Test
    public void testGetProductName() {
        // Test if the product name is returned correctly
        assertEquals("Test Product", orderDetail.getProductName());
    }

    @Test
    public void testGetSubtotal() {
        // Test if the subtotal is formatted and returned correctly
        assertEquals("100.00", orderDetail.getSubtotal());
    }

    @Test
    public void testGetShipping() {
        // Test if the shipping is formatted and returned correctly
        assertEquals("10.00", orderDetail.getShipping());
    }

    @Test
    public void testGetTax() {
        // Test if the tax is formatted and returned correctly
        assertEquals("15.00", orderDetail.getTax());
    }

    @Test
    public void testGetTotal() {
        // Test if the total is formatted and returned correctly
        assertEquals("125.00", orderDetail.getTotal());
    }
}
