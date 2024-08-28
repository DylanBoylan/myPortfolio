package abc_app;

public class ParkingSpace {

    private String id;
    private boolean accessible; // true if it's an accessible space
    private boolean available;
    private String company; // name of the company it is assigned to
    private boolean status; // true if reserved, false if open to booking
    private double price; // Price of the parking space, initialized to 0.0
    private String userName; // Username of the customer who booked the space

    @Override
	public String toString() {
		return "ParkingSpace [id=" + id + ", accessible=" + accessible + ", available=" + available + ", company="
				+ company + ", status=" + status + ", price=" + price + ", userName=" + userName + "]";
	}

	public ParkingSpace(String id, boolean accessible, String company) {
        this.id = id;
        this.accessible = accessible;
        this.available = true;
        this.company = company;
        this.status = false;
        this.price = 30.0; // Initialize price to 0.0
        this.userName = null; // Initialize userName to null (no customer initially)
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
