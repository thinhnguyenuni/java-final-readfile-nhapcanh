package fa.training.entities;

public class Pregnant extends Passenger {

//	private int gestationalAge;
//	private boolean pretermBirth;
	private String gestationalAge;
	private String pretermBirth;

	public Pregnant(int type, String passengerID, String name, String birthDate, String address, String phone,
			String testDate, String flightDate, String gestationalAge, String pretermBirth) {
		super(type, passengerID, name, birthDate, address, phone, testDate, flightDate);
		this.gestationalAge = gestationalAge;
		this.pretermBirth = pretermBirth;
	}

	public String getGestationalAge() {
		return gestationalAge;
	}

	public void setGestationalAge(String gestationalAge) {
		this.gestationalAge = gestationalAge;
	}

	public String isPretermBirth() {
		return pretermBirth;
	}

	public void setPretermBirth(String pretermBirth) {
		this.pretermBirth = pretermBirth;
	}

	@Override
	public void showInfo() {
		super.showMe();
		System.out.println("Gestational Age: " + gestationalAge);
		System.out.println("Preterm Birth: " + pretermBirth);
	}
}
