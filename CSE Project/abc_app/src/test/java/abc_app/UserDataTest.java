package abc_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {

    private UserData userData;

    @BeforeEach
    public void setUp() {
        userData = new UserData();
        userData.init();
    }

    @Test
    public void testInit() {
        ArrayList<User> users = userData.getUsers();
        assertEquals(7, users.size()); // Corrected from 5 to 7

        assertEquals("Dylan", users.get(0).getFirstName());
        assertEquals("Tugce", users.get(1).getFirstName());
        assertEquals("Mark", users.get(2).getFirstName());
        assertEquals("Sanaya", users.get(3).getFirstName());
        assertEquals("Elif", users.get(4).getFirstName());
        assertEquals("Joe", users.get(5).getFirstName());
        assertEquals("Jane", users.get(6).getFirstName());
    }

    @Test
    public void testGetSetFirstName() {
        userData.setFirstName("Caroline");
        assertEquals("Caroline", userData.getFirstName());
    }

    @Test
    public void testGetSetLastName() {
        userData.setLastName("Murphy");
        assertEquals("Murphy", userData.getLastName());
    }

    @Test
    public void testGetSetEmailAddress() {
        userData.setEmailAddress("caroline@murphy.com");
        assertEquals("caroline@murphy.com", userData.getEmailAddress());
    }

    @Test
    public void testGetSetPhoneNumber() {
        userData.setPhoneNumber("0821111111");
        assertEquals("0821111111", userData.getPhoneNumber());
    }

    @Test
    public void testGetSetUsername() {
        userData.setUsername("MurphyCaroline");
        assertEquals("MurphyCaroline", userData.getUsername());
    }

    @Test
    public void testGetSetPassword() {
        userData.setPassword("mypassCaroline");
        assertEquals("mypassCaroline", userData.getPassword());
    }

    @Test
    public void testRegister2() {
        userData.setFirstName("Charlie");
        userData.setLastName("Flynn");
        userData.setEmailAddress("charlie@flynn.com");
        userData.setPhoneNumber("0832222222");
        userData.setUsername("UserCharlie");
        userData.setPassword("Password123");

        String result = userData.register2();
        assertEquals("customer.xhtml", result);

        ArrayList<User> users = userData.getUsers();
        assertEquals(8, users.size()); // Adjusted from 6 to 8

        User newUser = users.get(7);

        assertEquals("Charlie", newUser.getFirstName());
        assertEquals("Flynn", newUser.getLastName());
        assertEquals("charlie@flynn.com", newUser.getEmail());
        assertEquals("0832222222", newUser.getPhone());
        assertEquals("UserCharlie", newUser.getUsername());
        assertEquals("Password123", newUser.getPassword());
    }

    @Test
    public void testGetObscuredPassword() {
        userData.setPassword("TestPassword");
        String obscuredPassword = userData.getObscuredPassword();
        assertEquals("T...d", obscuredPassword);
    }

    @Test
    public void testValidateUser() {
        // Correct username and password
        assertTrue(userData.validateUser("TYayman", "managelogin"));
        // Correct username, incorrect password
        assertFalse(userData.validateUser("TYayman", "password12"));
        // Correct username, correct password
        assertTrue(userData.validateUser("DBoylan", "managelogin"));
        // Incorrect username and password
        assertFalse(userData.validateUser("Mark", "mark123"));
        // Testing with empty strings
        assertFalse(userData.validateUser("", ""));
        // Null username and password
        assertFalse(userData.validateUser(null, null));
    }

    @Test
    public void testSetUsers() {
        ArrayList<User> newUsers = new ArrayList<>();
        newUsers.add(new User("Dylan", "Obrian", "0830000001", "dylano@hotmail.com", "UserDylan", "password1"));
        newUsers.add(new User("Selena", "Gomez", "0830000002", "selenaGomez@gmail.com", "UserSelena", "password2"));

        userData.setUsers(newUsers);

        ArrayList<User> users = userData.getUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testAddUser() {
        User newUser = new User("Hailey", "Bieber", "0830000003", "haileybieber@gmail.com", "UserHailey", "password3");

        userData.addUser(newUser);

        ArrayList<User> users = userData.getUsers();
        assertEquals(8, users.size()); // Adjusted from 6 to 8

        User addedUser = users.get(7);
        assertEquals("Hailey", addedUser.getFirstName());
        assertEquals("Bieber", addedUser.getLastName());
        assertEquals("0830000003", addedUser.getPhone());
        assertEquals("haileybieber@gmail.com", addedUser.getEmail());
        assertEquals("UserHailey", addedUser.getUsername());
        assertEquals("password3", addedUser.getPassword());
    }
}
