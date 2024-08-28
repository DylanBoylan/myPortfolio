package abc_app;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class BookingBean {

    private List<ParkingSpace> parkingSpaces;
    private List<ParkingSpace> filteredSpaces;
    private ParkingSpace selectedSpace;
    private String selectedID;
    private String paymentMethod;
    private List<ParkingSpace> parkingSpacesInUse;  
    private List<Booking> bookingsInUse;


    
    private List<Booking> bookings;
    
    private Date startDate, endDate;
    
    private boolean useAccessibleSpace;
    private String username;

   

    
    @PostConstruct
    public void init() {
        parkingSpaces = new ArrayList<>();
        parkingSpacesInUse = new ArrayList<>();
        bookings = new ArrayList<>();
        bookingsInUse = new ArrayList<>();

        //Total 40 spaces, 10 of each
        for (int i = 1; i <= 10; i++) 
        {
            parkingSpaces.add(new ParkingSpace("A" + i, isAccessible(i), null));
            parkingSpaces.add(new ParkingSpace("B" + i, isAccessible(i), null));
            parkingSpaces.add(new ParkingSpace("C" + i, isAccessible(i), null));
            parkingSpaces.add(new ParkingSpace("D" + i, isAccessible(i), null));
        }
        filterParkingSpaces();
        updateParkingSpacesInUse();
        updateBookingsInUse();


    }
    
    public void updateParkingSpacesInUse() {
        parkingSpacesInUse.clear();
        for (ParkingSpace space : parkingSpaces) {
            if (space.getUserName() != null) {
                String startDate = getBookingStartDate(space.getId());
                String endDate = getBookingEndDate(space.getId());
                if (startDate != null && endDate != null) {
                    parkingSpacesInUse.add(space);
                }
            }
        }
    }
    
    public void updateBookingsInUse() {
        bookingsInUse.clear();
        String loggedInUsername = getLoggedInUsername();
        
        // Loop through the bookings and add only those that belong to the logged-in user
        for (Booking booking : bookings) {
            for (ParkingSpace space : parkingSpaces) {
                if (booking.getId().equals(space.getId()) && loggedInUsername.equals(space.getUserName())) {
                    bookingsInUse.add(booking);
                    break; // Stop searching after finding the matching booking
                }
            }
        }
        System.out.println("Updated bookingsInUse list for user " + loggedInUsername + ": " + bookingsInUse);
    }


    public List<ParkingSpace> getParkingSpacesInUse() {
        return parkingSpacesInUse;
    }

    
    public boolean isAccessible(int index) 
    {
        //spaces 1, 6, 5 and 10 are accessible
    	if (index == 1 || index == 6 || index == 5 || index == 10)
    	{
    		return true;
    	}
    	
    	else
    	{
    		return false;
    	}
    }
     
   

    public void filterParkingSpaces() 
    {
        filteredSpaces = new ArrayList<>();

        for (ParkingSpace space : parkingSpaces) 
        {
            if (useAccessibleSpace) 
            {
            	//if the user is looking for accessible spaces, check if the space is available and accessible
                if (space.isAccessible() && space.isAvailable() && !space.isStatus()) 
                {
                    filteredSpaces.add(space);
                }
            } 
            else 
            {
                //if the user is looking for normal spaces, check if the space is available and not accessible 
                if (space.isAvailable() && !space.isAccessible() && !space.isStatus()) 
                {
                    filteredSpaces.add(space);
                }
            }
        }

    }
    
    //enhanced for loop to match the selected id with the parking space in the array
    public void findSelectedSpace() 
    {    	
        for (ParkingSpace space : parkingSpaces) 
        {
            if (space.getId().equals(selectedID)) 
            {
                selectedSpace = space;
                break;
            }
            
        }
    }
    
    
    public List<Booking> getBookingsInUse() {
        return bookingsInUse;
    }

    public String getSelectedID() {
        return selectedID;
    }

    public void setselectedID(String selectedID) {
        this.selectedID = selectedID;
    }
    
    public void setFilteredSpaces(List<ParkingSpace> filteredSpaces) {
        this.filteredSpaces = filteredSpaces;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public List<ParkingSpace> getFilteredSpaces() {
        return filteredSpaces;
    }

    public boolean isUseAccessibleSpace() {
        return useAccessibleSpace;
    }

    public void setUseAccessibleSpace(boolean useAccessibleSpace) {
        this.useAccessibleSpace = useAccessibleSpace;
        filterParkingSpaces(); // Filter the parking spaces when the checkbox is toggled
    }
    
    public List<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public ParkingSpace getSelectedSpace() {
        return selectedSpace;
    }

    public void setSelectedSpace(ParkingSpace selectedSpace) {
        this.selectedSpace = selectedSpace;
    }
    
    public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getStartDay(){
		return (DateUtils.formatDate(startDate));
	}
	public String getEndDay(){
		return (DateUtils.formatDate(endDate));
	}
	
	public String getPaymentMethod() {
	    return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
	    this.paymentMethod = paymentMethod;
	}
	public String getBookingStartDate(String spaceId) {
	    for (Booking booking : bookings) {
	        if (booking.getId().equals(spaceId)) {
	            return booking.getStartDate();
	        }
	    }
	    return null; // Return "N/A" if no booking is found
	}

	public String getBookingEndDate(String spaceId) {
	    for (Booking booking : bookings) {
	        if (booking.getId().equals(spaceId)) {
	            return booking.getEndDate();
	        }
	    }
	    return null; // Return "N/A" if no booking is found
	}

	
	public String saveBooking() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!validateDates()) {
            return null;
        }

        // Save booking (for now just a success message)
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, (bookings.get(0).getId()), "Your booking has been saved."));
        return "confirmation.xhtml";
    }
	
	public String register() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    updateParkingSpacesInUse();
	    updateBookingsInUse();
	    if (!validateDates()) {
	        return null;
	    }

	    //call the findSelectedSpace() function
	    findSelectedSpace();
	    

	    return bookSpace();
	}

	 private String getLoggedInUsername() {
	        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loggedInUser");
	    }

	
	
	
	//this method will book the space and save the booked space into a bookings arraylist
	public String bookSpace() 
	{
        FacesContext context = FacesContext.getCurrentInstance();
        String loggedInUsername = getLoggedInUsername();
        System.out.println(loggedInUsername);
                //error handling
        if (selectedSpace == null) 
        {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error no parking space selected", "No parking space selected."));
            return null;
        }
        
        //enhanced for loop to check if the selected parking space exists
        for (ParkingSpace space : parkingSpaces) 
        {
            if (space.getId().equals(selectedSpace.getId())) 
            {
            	
            	//this checks if the space is available 
                if (space.isAvailable()) 
                {
                	//reserves the spot
                    space.setAvailable(false);
                    space.setUserName(loggedInUsername);
                    updateParkingSpacesInUse();
                    updateBookingsInUse();
                    System.out.println("Space user set to: " + space.getUserName());
                    
                    
                    //gives the booking the id of the space and the start and end date
                    Booking booking = new Booking(selectedSpace.getId(), getStartDay(), getEndDay());
                    
                    //add object to the bookings aray
                    bookings.add(booking);
                    System.out.print(bookings);
                    System.out.print(parkingSpaces);
                    updateParkingSpacesInUse();
                    updateBookingsInUse();
                    
                    //success message for debugging
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                            ("Space " + booking.getId() + " has been reserved successfully"), 
                            "Your booking has been saved: "));
                    
                    //if cash is selected, go to the confirmation page, otherwise continue with paypal
                    if ("cash".equals(paymentMethod)) 
                    {
                        return "bookingCash.xhtml";
                    } 
                    
                    else if ("paypal".equals(paymentMethod)) 
                    {
                        return "paypal.xhtml";
                    }
                } 
                //else if (space.getCompany() != null) 
                //{
                //    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unavailable", "Parking space " + selectedSpace + " is reserved for company " + space.getCompany() + ", please choose another."));
                //} 
                else 
                {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unavailable", "Parking space " + selectedSpace + " is unavailable, please choose another."));
                }
                return null;  
            }
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Invalid parking space selected", "Invalid parking space selected."));
        return null;  
    }
	
	public List<Booking> getBookings() {
		return bookings;
	}
	public String cancelBooking() {
		FacesContext context = FacesContext.getCurrentInstance();
		String loggedInUsername = getLoggedInUsername();
		if (bookings.isEmpty()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Space doesn't exist",
					"Space may have been deleted already."));
			return null;
		}
		for (ParkingSpace space : parkingSpaces) {
			if (loggedInUsername.equals(selectedSpace.getUserName())) {
				for (Booking booking : bookings) {
					if (booking.getId().equals(space.getId())) {
						bookings.remove(0);
						space.setAvailable(true);
						space.setUserName(null);
						updateParkingSpacesInUse();
						updateBookingsInUse();
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
								"Your booking has been cancelled."));
						return "customer.xhtml";
					}
				}
			}
		}
		return null;
	}
	
	public String updateBooking() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    String loggedInUsername = getLoggedInUsername();

	    // Find the space ID associated with the current user
	    String idToRemove = null;
	    for (ParkingSpace space : parkingSpaces) {
	        if (loggedInUsername.equals(space.getUserName())) {
	            idToRemove = space.getId();
	            break; // Exit the loop once the space is found
	        }
	    }

	    // Remove the booking associated with the previous space ID
	    Booking bookingToRemove = null;
	    for (Booking booking : bookings) {
	        if (booking.getId().equals(idToRemove)) {
	            bookingToRemove = booking;
	            break;
	        }
	    }

	    if (bookingToRemove != null) {
	        bookings.remove(bookingToRemove);
	        System.out.println("Removed booking: " + bookingToRemove);
	    }

	    // Validate dates
	    if (!validateDates()) {
	        return null;
	    }

	    if (selectedSpace == null) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: No parking space selected", "No parking space selected."));
	        return null;
	    }

	    // Reserve the new space
	    for (ParkingSpace space : parkingSpaces) {
	        if (space.getId().equals(selectedID)) {
	            if (space.isAvailable()) {
	                // Reserve the spot
	                space.setAvailable(false);
	                space.setUserName(loggedInUsername);
	                selectedSpace = space;

	                // Create and add the new booking
	                Booking newBooking = new Booking(selectedID, DateUtils.formatDate(startDate), DateUtils.formatDate(endDate));
	                bookings.add(newBooking);
	                System.out.print(bookings);

	                // After updating, rebuild the parkingSpacesInUse list to reflect changes
	                updateParkingSpacesInUse();
	                updateBookingsInUse();

	                // Success message for debugging
	                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	                        ("Space " + newBooking.getId() + " has been reserved successfully"),
	                        "Your booking has been saved: "));
	                return "updateConfirmation.xhtml"; // Redirect after successful update
	            }
	        }
	    }

	    // Iterate through all parking spaces to find the one that matches the selected ID and the logged-in user's username
	    for (ParkingSpace space : parkingSpaces) {
	        if (loggedInUsername.equals(space.getUserName())) {
	            // Update the fields of the selected parking space
	            space.setAvailable(true);
	            space.setUserName(null);

	            // Rebuild the parkingSpacesInUse list to ensure consistency
	            updateParkingSpacesInUse();
	            updateBookingsInUse();

	            // Add a success message
	            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Your booking has been updated."));
	            return "updateConfirmation.xhtml"; // Redirect after successful update
	        }
	    }

	    // If no matching space was found, show an error message
	    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No matching booking found for the current user."));
	    return null;
	}


	
	

	
	//check to ensure correct dates have been selected
	private boolean validateDates() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (startDate == null || endDate == null) {
            FacesMessage errorMessage = new FacesMessage("Please select both start and end dates.");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, errorMessage);
            return false;
        }

        if (!startDate.before(endDate)) {
            FacesMessage errorMessage = new FacesMessage("End date must be after start date");
            errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, errorMessage);
            return false;
        }
        return true;
    }
}
