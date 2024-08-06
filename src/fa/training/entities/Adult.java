package fa.training.entities;

public class Adult extends Passenger {
	
	private String job;

	public Adult(int type, String passengerID, String name, String birthDate, String address, String phone,
			String testDate, String flightDate, String job) {
		super(type, passengerID, name, birthDate, address, phone, testDate, flightDate);
		this.job = job;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public void showInfo() {
		super.showMe();
		System.out.println("Job: " + job);
	}
}
