package abc_app;

public class Booking {

    @Override
	public String toString() {
		return "Booking [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", confirmed=" + confirmed
				+ "]";
	}

	private String id;
    private String startDate; //true if it's an accessible space
    private String endDate;
    private boolean confirmed;

    public Booking(String id, String startDate, String endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.confirmed = false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isConfirmed() {
        if (confirmed == true)
        {
        	return true;
        }
        else
        	return false;
    }
    
    public void setConfirmed() {
        this.confirmed = true;
    }
}
