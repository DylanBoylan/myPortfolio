package abc_app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty; //allows one managed bean to access properties of another managed bean.
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext; //adds messages that will be displayed on UI.(such as error messages)
import javax.faces.application.FacesMessage; //represents a message that is added to the FacesContext for display.

@ManagedBean // the class as a "managed bean" which means JSF will manage its lifecycle.
@SessionScoped // the bean's scope as session-based, works on requests in a user session.
public class RegistrationBean {
	private User user; // an instance of the User class.Representing the user who is registering.
	private User newMember;

	// note! objects like "User" that are not managed beans. no need for @ManagedProperty for User.
	@ManagedProperty(value = "#{userData}") // to inject a dependency,provides an instance of "UserData" to the
											// "RegistrationBean" class.
	private UserData userData; // an instance of the UserData class
	
	private String confirmPassword; //variable to store the second password entered by the user
	
	public RegistrationBean() { // the default constructor
		this.user = new User(); // initializes the "user" with a new User object
		this.newMember = new User();// initializes the "user" with a new User object

	}	
	public String getConfirmPassword() { //retrieve the value entered in the form
        return confirmPassword;
    }
	public void setConfirmPassword(String confirmPassword) { //assigns a value through the form
        this.confirmPassword = confirmPassword;
    }
	public User getUser() { // returns the current user object.
		return user;
	}
	public void setUser(User user) { // to assign a new User object to the "user".
		this.user = user;
	}
	public User getNewMember() { // returns the current newMember object.
		return newMember;
	}
	public void setNewMember(User newMember) { // to assign a new newMember object to the "newMember".
		this.newMember = newMember;
	}
	public UserData getUserData() { // returns the current userData
		return userData;
	}
	public void setUserData(UserData userData) { // assigns a new UserData object to the "userData" field.
		this.userData = userData;
	}
	public String registerMember() { // checks if the user inputs are valid.
	    FacesContext context = FacesContext.getCurrentInstance(); // FacesContext instance accesses to the JSF context
	    boolean valid = true; //will be "false" if any validation fails.
	    if (!isValidUsername(newMember.getUsername())) { 			//severity levels for messages
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Username", "Username must be between 2 and 12 characters long."));
	        valid = false; // Adds a message to the specified FacesContext. 
	                       //Null :it will appear in all components. 
	        				//and it creates a new FacesMessage object.
	    }
	    if (!isValidPassword(newMember.getPassword())) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Password", "Password must be at least 8 characters long, with at least one uppercase letter and one special character."));
	        valid = false;
	    }
	    if (valid) {
	        userData.addUser(newMember);
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Succesfully created user!", ""));
	        return null; // Stay on the same page and show error messages
	    } else {
	        
	        return null; // Stay on the same page and show error messages
	    }
	}
	
	public String register() { // checks if the user inputs are valid.
		    FacesContext context = FacesContext.getCurrentInstance(); // FacesContext instance accesses to the JSF context
		    boolean valid = true; //will be "false" if any validation fails.

		    if (!isValidUsername(user.getUsername())) { 			//severity levels for messages
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Username", "Username must be between 2 and 12 characters long."));
		        valid = false; // Adds a message to the specified FacesContext. 
		                       //Null :it will appear in all components. 
		        				//and it creates a new FacesMessage object.
		    }
		    if (!isValidPassword(user.getPassword())) {
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Password", "Password must be at least 8 characters long, with at least one uppercase letter and one special character."));
		        valid = false;
		    }
		    if (!user.getPassword().equals(confirmPassword)) { //The values of "password" and "confirmPassword" are compared
	            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password Mismatch", "Passwords do not match."));
	            valid = false;
	        }
		    if (!isValidEmail(user.getEmail())) {
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email", "Email address is not valid."));
		        valid = false;
		    }
		    if (!isValidName(user.getFirstName())) {
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid First Name", "First Name cannot be empty."));
		        valid = false;
		    }
		    if (!isValidName(user.getLastName())) {
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Last Name", "Last Name cannot be empty."));
		        valid = false;
		    }
		    if (!isValidPhone(user.getPhone())) {
		        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Phone Number", "Phone Number cannot be empty."));
		        valid = false;
		    }
		    if (valid) {
		        userData.addUser(user);
		        return "login.xhtml"; // Redirect after successful registration
		    } else {
		        
		        return null; // Stay on the same page and show error messages
		    }
		}
	// Name validation
	public boolean isValidName(String name) {
		return name != null && !name.trim().isEmpty(); // trim() ensures if any accidental spaces at the start or end.
	}
	// Phone validation
	public boolean isValidPhone(String phone) {
		return phone != null && !phone.trim().isEmpty();
	}
	// User-name validation
	public boolean isValidUsername(String username) { //the user-name is between 2 and 12 characters long and not null
		return username != null && username.length() >= 2 && username.length() <= 12;
	}
	// Password validation
	public boolean isValidPassword(String password) {
		if (password == null || password.length() < 8) {
			return false; // 8 characters long and not null.
		}
		// Boolean Flags
		boolean hasUpperCase = false; // will be "true" if there at least one uppercase letter.
		boolean hasSpecialChar = false; // will be "true" if there at least one special character.
		boolean hasOnlySpecialChars = true; // will be "false" if there any letters or digits.
		boolean hasSpaces = false; // will be "true" if there any spaces.

		for (char c : password.toCharArray()) { // iterates over each character in password.
			if (Character.isUpperCase(c)) { // checking the uppercase letter
				hasUpperCase = true;
			}

			if (!Character.isLetterOrDigit(c)) {
				hasSpecialChar = true;
			} else {
				hasOnlySpecialChars = false; // now password has no special character, we have letters or digits.
			}
			if (Character.isWhitespace(c)) {
				hasSpaces = true;
			}
		}
		return !hasOnlySpecialChars && hasUpperCase && hasSpecialChar && !hasSpaces; // method returns true if...
	} // NOT only special characters AND at least 1 uppercase letter AND at least 1 spec. char. AND no spaces.
	// Obscured password display
	public String getObscuredPassword() {
		String password = user.getPassword();
		return (firstLetter(password) + "..." + lastLetter(password));
	}
	//Helper methods for password hiding
	public String firstLetter(String s) {
		return s.substring(0, 1); // starting index 0 to index 1(not included)
	}
	public String lastLetter(String s) {
		int length = s.length();
		return s.substring(length - 1, length); // starting at last index to whole length(not included)
	}
	// Email validation
	public boolean isValidEmail(String email) {
		if (email == null || email.isEmpty()) { // checking if it is null(absence of a value) or empty(a string object
												// with no characters)
			return false;
		}
		int atIndex = email.indexOf('@'); // returns index of first @ character.if not found @, it sets -1.
		int dotIndex = email.lastIndexOf('.'); // returns index of last occurrence of "." ( last "." after "@" )
		// '@' should be present and '.' should come after '@'
		return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1; // returns true if...
		// @ not first character AND "." comes after "@", not immediately, after 1 char.
		// AND "." is not the last character
	}
}