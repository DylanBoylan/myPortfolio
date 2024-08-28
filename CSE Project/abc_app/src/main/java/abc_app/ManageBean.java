package abc_app;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


// This class will check the user input, and will call userData.validateUser to verify valid details were entered 
// and then if the user is a member, customer or management
@ManagedBean
@SessionScoped
public class ManageBean {
	String userName, password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String validateUserLogin() {
		UserData userData = Helper.getBean("userData", UserData.class);
		// Checks to see if the user logging in is a member, customer or management. Dylan, Mark, Tugce are management, Elif and Sanaya are members
		if (userData.validateUser(userName, password)) 
		{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", userName);
			if (this.userName.equals("DBoylan") || this.userName.equals("TYayman") || this.userName.equals("MLat"))
				return "home.xhtml";
			else if (this.userName.equals("SGupta") ||this.userName.equals("EAtesKarakas") || this.userName.equals("JDoe") || this.userName.equals("JSmith") || this.userName.equals("newMember") )
				return "member.xhtml";
			else
				return "customer.xhtml";
	    } 
		else 
	    {
			// If the user is not valid, then it will give an error message
	    	FacesContext context=FacesContext.getCurrentInstance();
			FacesMessage errorMessage=
					new FacesMessage ("Invalid Username/Password Combination");
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null,  errorMessage);
			return (null);
	    }
	}
	
	public void logout() {
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("userName");
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("password");
	    
	    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout successful", null));
	    try {
	        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}