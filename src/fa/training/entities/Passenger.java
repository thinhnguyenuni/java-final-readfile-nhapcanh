package fa.training.entities;

public abstract class Passenger {
	
	private int type;
	private String passengerID;
	private String name;
	private String birthDate;
	private String address;
	private String phone;
	private String testDate;
	private String flightDate;

	public Passenger(int type, String passengerID, String name, String birthDate, String address, String phone,
			String testDate, String flightDate) {
		this.type = type;
		this.passengerID = passengerID;
		this.name = name;
		this.birthDate = birthDate;
		this.address = address;
		this.phone = phone;
		this.testDate = testDate;
		this.flightDate = flightDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPassengerID() {
		return passengerID;
	}

	public void setPassengerID(String passengerID) {
		this.passengerID = passengerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public abstract void showInfo();
	
	public void showMe() {
		System.out.println("Type: " + type);
		System.out.println("PassengerID: " + passengerID);
		System.out.println("Name: " + name);
		System.out.println("BirthDate: " + birthDate);
		System.out.println("Address: " + address);
		System.out.println("Phone: " + phone);
		System.out.println("TestDate: " + testDate);
		System.out.println("FlightDate: " + flightDate);
	}

}
