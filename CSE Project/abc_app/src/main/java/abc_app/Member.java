package abc_app;

public class Member {
	    
	    private String company;
	    private String email;
	    private String userName;

	    
	    public Member(String company, String email, String userName) {
	        this.company = company;
	        this.email = email;
	        this.userName = userName;
	    }

	    
	    public String getCompany() {
	        return company;
	    }

	    public void setCompany(String company) {
	        this.company = company;
	    }

	    
	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    
	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    @Override
	    public String toString() {
	        return "Member{" +
	                "company='" + company + '\'' +
	                ", email='" + email + '\'' +
	                ", userName='" + userName + '\'' +
	                '}';
	    }
	

}
