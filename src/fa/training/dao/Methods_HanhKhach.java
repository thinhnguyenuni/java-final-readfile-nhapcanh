package fa.training.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import fa.training.entities.Adult;
import fa.training.entities.Children;
import fa.training.entities.Passenger;
import fa.training.entities.Pregnant;
import fa.training.exception.InvalidTestDayException;
import fa.training.utils.ConnectionUtil;

public class Methods_HanhKhach {

	public static int numberRecords = 0;

	/**
	 * THINHNV30 1993-03-03 THÊM THÔNG TIN HÀNH KHÁCH TỪ FILE
	 * 
	 * @throws Exception
	 */
	public static void insert() throws Exception {

		File f = new File("D:\\HOC_TAP\\FILE_FULLSTACK\\JPE\\JAVA CODE\\JPE_1\\HANH_KHACH_REMIX\\src\\HanhKhach_1.csv");
		String line = "";

		try {
			Connection connection = ConnectionUtil.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO Passenger_1 (Type,PassengerID, Name, BirthDate, Address, Phone, TestDate, FlightDate, Job, School, Grade, GestationalAge, PretermBirth) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			BufferedReader br = Files.newBufferedReader(f.toPath(), StandardCharsets.UTF_8);
//			int numberRecords = 0;
			while ((line = br.readLine()) != null) {

				String[] data = line.split(",");

				String type = data[0];
				String passengerID = data[1];
				String name = data[2];
				String birthDate = data[3];
				String address = data[4];
				String phone = data[5];
				String testDateStr = data[6];
				String flightDate = data[7];

				// Đây là biến boolean cho biết bản ghi có hợp lệ hay không
				boolean isValid = true;
				try {
					// Kiểm tra PassengerID
					if (!passengerID.matches("PS\\d{3}")) {
						System.out.println("PassengerID must be in format of 'PSxxx' for Passenger " + passengerID);
						continue; // Bỏ qua dòng dữ liệu không hợp lệ
					}

					// Kiểm tra TestDate
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date testDate = dateFormat.parse(testDateStr);
					Date currentDate = new Date();
					if (testDate.after(currentDate)) {
						throw new InvalidTestDayException("Test Date of Covid must be less than current date!!!");

					}

					// Kiểm tra PassengerID trùng lặp
					if (isPassengerIDDuplicate(connection, passengerID)) {
						System.out.println("Passenger " + passengerID + " has duplicate ID");
						continue; // Bỏ qua dòng dữ liệu trùng lặp
					}
				} catch (InvalidTestDayException e) { // Đây là một lớp ngoại lệ cho biết lỗi xác thực

					// This is a statement that sets the isValid variable to false
					isValid = false;

					// Đây là câu lệnh in thông báo lỗi ra bàn điều khiển
					System.out.println(e.getMessage());
				}
				if (isValid) {

					// Nếu tất cả kiểm tra đều qua, thì thực hiện insert
					preparedStatement.setString(1, type);
					preparedStatement.setString(2, passengerID);
					preparedStatement.setString(3, name);
					preparedStatement.setString(4, birthDate);
					preparedStatement.setString(5, address);
					preparedStatement.setString(6, phone);
					preparedStatement.setString(7, testDateStr);
					preparedStatement.setString(8, flightDate);

					preparedStatement.setString(9, data[8]); // Job
					preparedStatement.setString(10, data[9]); // School
					preparedStatement.setString(11, data[10]); // Grade

					preparedStatement.setString(12, data[11]); // GestationalAge

//					preparedStatement.setBoolean(13, Boolean.parseBoolean(data[12])); // PretermBirth
					preparedStatement.setString(13, data[12]); // PretermBirth

					preparedStatement.executeUpdate();
					numberRecords++;
					
				}
			}

			br.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Program have an unexpected error occurred !!!");
			e.printStackTrace();
		}
		if (numberRecords == 0) {
			System.out.println("Insert that bai");
		}
		System.out.println("Số dòng insert thành công là: " + numberRecords);
	}

	/**
	 * THINHNV30 1993-03-03 HÀM KIỂM TRA TRÙNG LẶP PassengerID
	 */
	private static boolean isPassengerIDDuplicate(Connection connection, String passengerID) throws SQLException {

		String query = "SELECT PassengerID FROM Passenger_1 WHERE PassengerID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, passengerID);
		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.next();
	}

	/**
	 * THINHNV30 1993-03-03 Lấy thông tin từ database
	 */
	public static ArrayList<Passenger> getPassengersFromDatabase(Connection connection) {
		ArrayList<Passenger> passengers = new ArrayList<>();
		try {
			String query = "SELECT * FROM Passenger_1";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				// Tạo đối tượng Passenger từ dữ liệu ResultSet
				int type = resultSet.getInt("Type");
				String passengerID = resultSet.getString("PassengerID");
				String name = resultSet.getString("Name");
				String birthDate = resultSet.getString("BirthDate");
				String address = resultSet.getString("Address");
				String phone = resultSet.getString("Phone");
				String testDate = resultSet.getString("TestDate");
				String flightDate = resultSet.getString("FlightDate");
				String job = resultSet.getString("Job");
				String school = resultSet.getString("School");
				String grade = resultSet.getString("Grade");
				String gestationalAge = resultSet.getString("GestationalAge");
				String pretermBirth = resultSet.getString("PretermBirth");

				if (type == 1) {
					Adult adult = new Adult(type, passengerID, name, birthDate, address, phone, testDate, flightDate,
							job);
					passengers.add(adult);
				} else if (type == 2) {
					Children child = new Children(type, passengerID, name, birthDate, address, phone, testDate,
							flightDate, school, grade);
					passengers.add(child);
				} else if (type == 3) {
					Pregnant pregnant = new Pregnant(type, passengerID, name, birthDate, address, phone, testDate,
							flightDate, gestationalAge, pretermBirth);
					passengers.add(pregnant);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return passengers;
	}

	/**
	 * THINHNV30 1993-03-03 GIỚI THIỆU THÔNG TIN CỦA CÁC HÀNH KHÁCH HIỆN CÓ
	 */
	public static void showInformation(ArrayList<Passenger> passengers) {
		for (Passenger passenger : passengers) {
			passenger.showInfo();
			System.out.println(); // In một dòng trống để phân tách giữa các hành khách
		}
	}

	/**
	 * THINHNV30 1993-03-03 GIỚI THIỆU THÔNG TIN CỦA CÁC HÀNH KHÁCH HIỆN CÓ
	 */
	public static void getInformation() {

		Connection conn = ConnectionUtil.getConnection();
		// Tạo một danh sách hành khách
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		passengers = getPassengersFromDatabase(conn);

		// Gọi phương thức showInformation để hiển thị thông tin của hành khách
		showInformation(passengers);
	}

	/**
	 * THINHNV30 1993-03-03 CẬP NHẬT LỊCH TRÌNH BAY
	 */
	public static void updateFlighDate() {

		Connection conn = ConnectionUtil.getConnection();
		// Tạo một danh sách hành khách
		ArrayList<Passenger> passengers = new ArrayList<Passenger>();
		passengers = getPassengersFromDatabase(conn);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (Passenger passenger : passengers) {
			try {
				Date testDate = dateFormat.parse(passenger.getTestDate());
				Date flightDate = dateFormat.parse(passenger.getFlightDate());

				// Kiểm tra nếu ngày test Covid quá 1 tuần so với ngày khởi hành
				if (testDate.getTime() < flightDate.getTime() + 7 * 24 * 60 * 60 * 1000) {
					flightDate.setTime(flightDate.getTime() - 2 * 24 * 60 * 60 * 1000); // Lùi lại 2 ngày
					passenger.setFlightDate(dateFormat.format(flightDate));
//	                    System.out.println("Sau chỉnh sửa" + passenger.getFlightDate());

					try (Connection con = ConnectionUtil.getConnection();
							PreparedStatement prst = con
									.prepareStatement("UPDATE passenger_1 SET FlightDate=? WHERE  passengerID =?")) {

						prst.setString(1, passenger.getFlightDate());
						prst.setString(2, passenger.getPassengerID());

						prst.executeUpdate();

					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

				// Kiểm tra nếu hành khách là người thân (cùng địa chỉ)
				if (passenger instanceof Adult) {
					Adult adult = (Adult) passenger;
					for (Passenger relative : passengers) {
						if (relative instanceof Adult && !relative.equals(adult)
								&& relative.getAddress().equals(adult.getAddress())) {

							Date relativeFlightDate = dateFormat.parse(relative.getFlightDate());
							relativeFlightDate.setTime(relativeFlightDate.getTime() - 2 * 24 * 60 * 60 * 1000);
							relative.setFlightDate(dateFormat.format(relativeFlightDate));

							try (Connection con = ConnectionUtil.getConnection();
									PreparedStatement prst = con.prepareStatement(
											"UPDATE passenger_1 SET FlightDate=? WHERE  passengerID =?")) {

								prst.setString(1, passenger.getFlightDate());
								prst.setString(2, passenger.getPassengerID());

								prst.executeUpdate();

							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		showInformation(passengers);
	}

	/**
	 * THINHNV30 1993-03-03 SẮP XẾP DANH SÁCH HÀNH KHÁCH
	 */
	public static void sortHanhKhach() {
		try {
			Connection connection = ConnectionUtil.getConnection();

			// Truy vấn dữ liệu hành khách từ cơ sở dữ liệu
			ArrayList<Passenger> passengers = getPassengersFromDatabase(connection);

			// Sắp xếp danh sách hành khách theo yêu cầu
			Collections.sort(passengers, new Comparator<Passenger>() {
				@Override
				public int compare(Passenger p1, Passenger p2) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						// So sánh theo FlightDate giảm dần
						int flightDateComparison = dateFormat.parse(p2.getFlightDate())
								.compareTo(dateFormat.parse(p1.getFlightDate()));
						if (flightDateComparison != 0) {
							return flightDateComparison;
						}

						// So sánh theo PassengerID tăng dần
						return p1.getPassengerID().compareTo(p2.getPassengerID());
					} catch (Exception e) {
						e.printStackTrace();
						return 0;
					}
				}
			});

			// Hiển thị danh sách đã sắp xếp
			showInformation(passengers);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * THINHNV30 1993-03-03 XÓA THÔNG TIN HÀNH KHÁCH
	 */
	public static void deleteHK() {
		
		try (
			
			Connection connection = ConnectionUtil.getConnection();
			// Xóa hành khách chưa tiến hành test Covid (TestDate có giá trị null)
//			String query = "DELETE FROM Passenger_1 WHERE TestDate IS NULL";
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Passenger_1 WHERE TestDate IS NULL")){
			preparedStatement.executeUpdate();

			// Đóng kết nối
//			connection.close();

			System.out.println("Deleted passengers with null TestDate.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
	}
	
	/**
	 * THINHNV30 1993-03-03 XÓA THÔNG TIN HÀNH KHÁCH
	 */
	public static void deleteAll() {
		
		try (
			
			Connection connection = ConnectionUtil.getConnection();
			// Xóa hành khách chưa tiến hành test Covid (TestDate có giá trị null)
//			String query = "DELETE FROM Passenger_1 ";
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Passenger_1 ")){
			preparedStatement.executeUpdate();

			// Đóng kết nối
//			connection.close();

			System.out.println("Deleted passengers !!!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
	}
}
