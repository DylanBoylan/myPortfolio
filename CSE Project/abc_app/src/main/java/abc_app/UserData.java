package abc_app;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

// This class will create and store user-created (using the registration page), and pre-defined users, 
// also this class will allow checks to verify user-entered username and password, and will allow 
// checks for user-inputs in the registration page (eg. 2-12 letters for password)..
@ManagedBean
@SessionScoped
public class UserData {

	private ArrayList<User> users;

    private String firstName, lastName, emailAddress, password, phoneNumber, username;

    
	 public UserData() {
	        this.users = new ArrayList<>();  // Initialize the list here
	 }

    // This method will create 3 pre-defined management users and 2 member users, and put them in the users arraylist
	@PostConstruct
	public void init() {
		users = new ArrayList<User>();
		User firstUser = new User("Dylan", "Boylan", "0831111111", "dylanboylan@tus.ie", "DBoylan", "managelogin");
		users.add(firstUser);
		User secondUser = new User("Tugce", "Yayman", "0831111111", "tugceyayman@tus.ie", "TYayman", "managelogin");
		users.add(secondUser);
		User thirdUser = new User("Mark", "Lat", "0831111111", "marklat@tus.ie", "MLat", "managelogin");
		users.add(thirdUser);
		User fourthUser = new User("Sanaya", "Gupta", "0831111111", "sanayagupta@tus.ie", "SGupta", "managelogin");
		users.add(fourthUser);
		User fifthUser = new User("Elif", "Ates-Karakas", "0831111111", "elifateskarakas@tus.ie", "EAtesKarakas", "managelogin");
		users.add(fifthUser);
		User sixthUser = new User("Joe", "Smith", "0831111111", "johnsmith@abc.com", "JSmith", "managelogin");
		users.add(sixthUser);
		User seventhUser = new User("Jane", "Doe", "0831111111", "janedoe@abc.com", "JDoe", "managelogin");
		users.add(seventhUser);
		
	
	}
	
	public String getFirstName()
	{
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getEmailAddress() 
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }
    
    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public ArrayList<User> getUsers() 
    {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
    
    public void addUser(User user) {
        users.add(user);
    }

	public String getObscuredPassword() 
    {
        return (firstLetter(password) + "..." + lastLetter(password));
    }
    
    public String register2() 
    {
        // Creates a new user, based off of the inputs in the XHTML input fields
        User newUser = new User(firstName, lastName, phoneNumber, emailAddress, username, password );
        users.add(newUser);
 
        // Once the user is added, it will automatically log the user in and send them to the main customer page
        return "customer.xhtml";
    }

    //These two methods are for encrypting passwords, it will save the first letter and last letter of a password, and print a "..." in between them for security
    private String firstLetter(String s) 
    {
        return (s.substring(0, 1));
    }

    private String lastLetter(String s) 
    {
        int length = s.length();
        return (s.substring(length - 1, length));
    }

    // This method is for Login, this will check each user, to see if the username and password entered by the user, matches a created users details.
	public boolean validateUser(String userName, String password) 
	{
		boolean validUser=false;
		for (User user:users) 
		{
			if((user.getUsername().equals(userName))&&(user.getPassword().equals(password) ))
			{
				validUser=true;
				break;
			}
		}
		return validUser;
	}

	
}