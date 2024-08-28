package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManageBeanTest {

    private ManageBean manageBean;

    @BeforeEach
    public void setUp() {
        manageBean = new ManageBean();
    }

    
    @Test
    public void testSetAndGetUsername() {
        String expected = "TestUser";
        manageBean.setUserName(expected);
        String actual = manageBean.getUserName();
        assertEquals(expected, actual, "The username should be set and retrieved correctly.");
    }

    @Test
    public void testSetAndGetPassword() {
        String expected = "password123";
        manageBean.setPassword(expected);
        String actual = manageBean.getPassword();
        assertEquals(expected, actual, "The password should be set and retrieved correctly.");
    }

  
    
}
