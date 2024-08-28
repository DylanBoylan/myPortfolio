package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingManageBeanTest {

    private ParkingManageBean bean;

    @BeforeEach
    public void setUp() {
        bean = new ParkingManageBean();
    }

    @Test
    public void testGetAndSetSelectedSpaceId() {
        bean.setSelectedSpaceId("A1");
        assertEquals("A1", bean.getSelectedSpaceId());
    }

    @Test
    public void testGetAndSetSelectedStatus() {
        bean.setSelectedStatus("booking");
        assertEquals("booking", bean.getSelectedStatus());
    }

    @Test
    public void testGetParkingSpaces() {
        assertNotNull(bean.getParkingSpaces());
        assertEquals(40, bean.getParkingSpaces().size()); // 10 spaces in each of A, B, C, D
    }


}
