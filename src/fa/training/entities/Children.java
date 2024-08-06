package fa.training.entities;

public class Children extends Passenger {
	
	private String school;
	private String grade;

	public Children(int type, String passengerID, String name, String birthDate, String address, String phone,
			String testDate, String flightDate, String school, String grade) {
		super(type, passengerID, name, birthDate, address, phone, testDate, flightDate);
		this.school = school;
		this.grade = grade;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public void showInfo() {
		super.showMe();
		System.out.println("School: " + school);
		System.out.println("Grade: " + grade);
	}
}
