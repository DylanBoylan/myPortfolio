package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals("", user.getFirstName());
        assertEquals("", user.getLastName());
        assertEquals("", user.getPhone());
        assertEquals("", user.getEmail());
        assertEquals("", user.getUsername());
        assertEquals("", user.getPassword());
    }

    @Test
    public void testParameterizedConstructor() {
        User user = new User("Tugce", "Yayman", "0898765432", "tugce.yay@gmail.com", "yaytugce", "password123");
        assertEquals("Tugce", user.getFirstName());
        assertEquals("Yayman", user.getLastName());
        assertEquals("0898765432", user.getPhone());
        assertEquals("tugce.yay@gmail.com", user.getEmail());
        assertEquals("yaytugce", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testGetSetFirstName() {
        user.setFirstName("Joe");
        assertEquals("Joe", user.getFirstName());
    }

    @Test
    public void testGetSetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testGetSetPhone() {
        user.setPhone("0812345678");
        assertEquals("0812345678", user.getPhone());
    }

    @Test
    public void testGetSetEmail() {
        user.setEmail("joe.smith@example.com");
        assertEquals("joe.smith@example.com", user.getEmail());
    }

    @Test
    public void testGetSetUsername() {
        user.setUsername("joesmith");
        assertEquals("joesmith", user.getUsername());
    }

    @Test
    public void testGetSetPassword() {
        user.setPassword("joeSmithPassword");
        assertEquals("joeSmithPassword"
        		+ "", user.getPassword());
    }
}
