package abc_app;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@SessionScoped
public class ParkingManageBean {

    private List<ParkingSpace> parkingSpaces;
    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;
    private String selectedSpaceId;
    private String selectedStatus;
    private List<ParkingSpace> reservedCompanySpaces;// contains only the spaces those are reseved


    public ParkingManageBean() {
    	reservedCompanySpaces = new ArrayList<>();
        // Re-create members and assign parking spaces
        member1 = new Member("Gupta", "sanayagupta@tus.ie", "SGupta");
        member2 = new Member("Karakas", "sanayagupta@tus.ie", "EAtesKarakas");
        member3 = new Member("Smith", "johnsmith@abc.com", "JSmith");
        member4 = new Member("Doe", "janedoe@abc.com", "JDoe");

        BookingBean bookingBean = Helper.getBean("bookingBean", BookingBean.class);

        parkingSpaces = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            parkingSpaces.add(new ParkingSpace("A" + i, bookingBean.isAccessible(i), member1.getCompany()));
            parkingSpaces.add(new ParkingSpace("B" + i, bookingBean.isAccessible(i), member2.getCompany()));
            parkingSpaces.add(new ParkingSpace("C" + i, bookingBean.isAccessible(i), member3.getCompany()));
            parkingSpaces.add(new ParkingSpace("D" + i, bookingBean.isAccessible(i), member4.getCompany()));
        }
        updateReservedCompanySpaces();
    }

    
    public void updateReservedCompanySpaces() {
        reservedCompanySpaces.clear(); // Clear the list first
        for (ParkingSpace space : parkingSpaces) {
            if (space.isStatus()) { // Check if the space is reserved by a company
                reservedCompanySpaces.add(space);
            }
        }
    }
    
    public List<ParkingSpace> getReservedCompanySpaces() {
        return reservedCompanySpaces;
    }
   


    public List<ParkingSpace> getReservedSpaces() {
        List<ParkingSpace> reservedSpaces = new ArrayList<>();
        String loggedInUsername = getLoggedInUsername();

        if (loggedInUsername == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User is not logged in."));
            return reservedSpaces;
        }

        String company = getCompanyForUser(loggedInUsername);
        if (company == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No company associated with the user."));
            return reservedSpaces;
        }

        for (ParkingSpace space : parkingSpaces) {
            if (space.isStatus() && company.equals(space.getCompany())) {
                reservedSpaces.add(space);
            }
        }
        return reservedSpaces;
    }


    public List<ParkingSpace> getOpenForBookingSpaces() {
        List<ParkingSpace> openSpaces = new ArrayList<>();
        String loggedInUsername = getLoggedInUsername();

        if (loggedInUsername == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User is not logged in."));
            return openSpaces;
        }

        String company = getCompanyForUser(loggedInUsername);
        if (company == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No company associated with the user."));
            return openSpaces;
        }

        for (ParkingSpace space : parkingSpaces) {
            // Ensure that spaces with null companies are considered as open for booking
            if (!space.isStatus() && (space.getCompany() == null || space.getCompany().equals(company))) {
                openSpaces.add(space);
            }
        }
        return openSpaces;
    }



    private String getLoggedInUsername() {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
    }


    private String getCompanyForUser(String username) {
        if (username == null) {
            return null;
        }

        switch (username) {
            case "SGupta":
                return member1.getCompany();
            case "EAtesKarakas":
                return member2.getCompany();
            case "JSmith":
                return member3.getCompany();
            case "JDoe":
                return member4.getCompany();
            default:
                return null;
        }
    }

    public void updateParkingSpace() {
        for (ParkingSpace space : parkingSpaces) {
            if (space.getId().equals(selectedSpaceId)) {

                String loggedInUsername = getLoggedInUsername();
                if (loggedInUsername == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User is not logged in."));
                    return;
                }

                if ("booking".equals(selectedStatus)) {
                    space.setStatus(false); 
                    space.setCompany(getCompanyForUser(loggedInUsername));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Parking space " + selectedSpaceId + " is now open for booking."));
                } else if ("staff".equals(selectedStatus)) {
                    space.setStatus(true); 
                    space.setCompany(getCompanyForUser(loggedInUsername)); //associate with the logged-in user's company
                    space.setPrice(0.0); 

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Parking space " + selectedSpaceId + " is now reserved for staff."));
                }

                //update the reserved spaces list
                updateReservedCompanySpaces();
                break;
            }
        }
    }


    
    public void updatePrice(String spaceId, double newPrice) {
        for (ParkingSpace space : parkingSpaces) {
            if (space.getId().equals(spaceId)) {
                if (space.isStatus()) {
                    //if the parking space is reserved, not allowed to update the price
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", 
                                             "Cannot update price for reserved parking spaces."));
                    return;
                } else {
                    //if not reserved, update the price
                    space.setPrice(newPrice);
                    updateReservedCompanySpaces();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", 
                                             "Price updated for parking space " + spaceId));
                }
                return;
            }
        }
        // if spaceID doesn't match any of the spaces
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", 
                                 "Parking space not found."));
    }


    public List<ParkingSpace> getParkingSpacesForLoggedInUser() {
        String loggedInUsername = getLoggedInUsername();  
        String company = getCompanyForUser(loggedInUsername);  //get the company name based on the username
        
        if (company == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User is not associated with a company."));
            return new ArrayList<>();  // return an empty list if the user has no associated company
        }

        List<ParkingSpace> companySpaces = new ArrayList<>();
        for (ParkingSpace space : parkingSpaces) {
        	 if (space.getCompany() != null && space.getCompany().equals(company)) {
                companySpaces.add(space);
            }
        }
        return companySpaces;
    }




    // Getters and setters
    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public String getSelectedSpaceId() {
        return selectedSpaceId;
    }

    public void setSelectedSpaceId(String selectedSpaceId) {
        this.selectedSpaceId = selectedSpaceId;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }
}
