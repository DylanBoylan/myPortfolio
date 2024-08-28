package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationBeanTest {

	private RegistrationBean registrationBean;
    private User user;
    private UserData userData;

    @BeforeEach
    public void setUp() {
        registrationBean = new RegistrationBean();
        user = new User();
        userData = new UserData(); // Initialize UserData
        registrationBean.setUser(user);
        registrationBean.setUserData(userData); // Set the UserData instance
    }

    @Test
    public void testGetObscuredPassword() {
        user.setPassword("Password123!");
        String obscuredPassword = registrationBean.getObscuredPassword();
        assertEquals("P...!", obscuredPassword);
    }

    @Test
    public void testRegister_Success() {
        user.setUsername("TugceYayman");
        user.setPassword("Pass13word!");
        user.setEmail("tugce.yay@gmail.com");

        // Call the register method
        String result = registrationBean.register();

        // Assert the result
        assertEquals("login.xhtml", result);

        // Verify that the user was added to userData
        assertEquals(1, userData.getUsers().size());
        assertEquals(user, userData.getUsers().get(0));
    }

    @Test
    public void testRegister_FailureInvalidUsername() {
        user.setUsername("D");
        user.setPassword("PassDylan_");
        user.setEmail("dylan.tus@gmail.com");
        String result = registrationBean.register();
        assertEquals("registration-error", result);
    }

    @Test
    public void testRegister_FailureInvalidPassword() {
        user.setUsername("SanayaGupta_");
        user.setPassword("passwordsg");
        user.setEmail("gupta.sanaya1@hotmail.com");
        String result = registrationBean.register();
        assertEquals("registration-error", result);
    }

    @Test
    public void testRegister_InvalidEmailFailure() {
        user.setUsername("markJayson*7");
        user.setPassword("jaysonM!");
        user.setEmail("mark-email");
        String result = registrationBean.register();
        assertEquals("registration-error", result);
    }

    @Test
    public void testValidUserName() {
        assertEquals(true,registrationBean.isValidUsername("ElifAtes"));
        assertEquals(true,registrationBean.isValidUsername("KarakasElif_"));
    }
    
    
    @Test
    public void testBoundaryUserName() {
        // Minimum length User Name
    	 assertEquals(true,registrationBean.isValidUsername("Tu"));
        // Maximum length User Name
    	 assertEquals(true,registrationBean.isValidUsername("UsernmTugce"));
    }

    @Test
    public void testInvalidUserName() {
    	// Null User Name
    	 assertEquals(false,registrationBean.isValidUsername(null));
    	// Short User Name
    	 assertEquals(false,registrationBean.isValidUsername("T"));
        // Long User Name
    	 assertEquals(false,registrationBean.isValidUsername("userTugceYayman13"));
        // Empty User Name
        assertEquals(false,registrationBean.isValidUsername(""));
    }


    @Test
    public void testValidPassword() {
        assertTrue(registrationBean.isValidPassword("PassTugceYay13!"));
    }

    @Test
    public void testInvalidPassword() {
    	// Null password
    	assertFalse(registrationBean.isValidPassword(null));
    	// Short password (less than 8 characters, has no special char, no upper case)
        assertFalse(registrationBean.isValidPassword("pass"));
        // Short password (exactly 8 characters, has special char, no upper case)
        assertFalse(registrationBean.isValidPassword("tugce13_"));
        // All upper case, no special char
        assertFalse(registrationBean.isValidPassword("TUGCEYAY0"));
        // No special char
        assertFalse(registrationBean.isValidPassword("TugceYayman13"));
        // Password with all digits
        assertFalse(registrationBean.isValidPassword("12345678"));
        // Password with space
        assertFalse(registrationBean.isValidPassword("Pass word!"));
        // Password with only special characters
        assertFalse(registrationBean.isValidPassword("!@#$%^&*()"));
    }

    @Test
    public void testBoundaryPassword() {
        // Exactly 8 characters, valid password
        assertEquals(true,registrationBean.isValidPassword("Pa**word"));
    
    }

    @Test
    public void testValidEmail() {
        assertEquals(true,registrationBean.isValidEmail("yayman.tugce@gmail.com"));
        assertEquals(true,registrationBean.isValidEmail("tugce.yayman+13+college@gmail.com"));
        assertEquals(true,registrationBean.isValidEmail("TugceYayman@tus.ie"));
        assertEquals(true,registrationBean.isValidEmail("Tugce@tus.ie.com"));
        assertEquals(true,registrationBean.isValidEmail("name@subdomain.tus.ie"));
    }

    @Test
    public void testInvalidEmail() {
    	// Null email
    	assertEquals(false,registrationBean.isValidEmail(null));
    	// Email missing domain and @
    	assertEquals(false,registrationBean.isValidEmail("tugce-yayman"));
        // Email Missing domain
    	assertEquals(false,registrationBean.isValidEmail("yaymantugce@.com"));
        // Email Missing @
    	assertEquals(false,registrationBean.isValidEmail("tugceyayman.com"));
        // Email with invalid domain
    	assertEquals(false,registrationBean.isValidEmail("tugce@yayman"));
    }

    @Test
    public void testFirstLetter() {
        assertEquals("Y", registrationBean.firstLetter("YaymanTugce"));
    }

    @Test
    public void testLastLetter() {
        assertEquals("e", registrationBean.lastLetter("YaymanTugce"));
    }

    @Test
    public void testGetUser() {
        assertEquals(user, registrationBean.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = new User();
        registrationBean.setUser(newUser);
        assertEquals(newUser, registrationBean.getUser());
    }
    
    @Test
    public void testGetUserData() {
        assertEquals(userData, registrationBean.getUserData());
    }
    
    @Test
    public void testSetUserData() {
        UserData newUserData = new UserData();
        registrationBean.setUserData(newUserData);
        assertEquals(newUserData, registrationBean.getUserData());
    }
}
