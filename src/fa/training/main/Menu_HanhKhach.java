package fa.training.main;

import java.util.Scanner;

import fa.training.dao.Methods_HanhKhach;


public class Menu_HanhKhach {

	
	public static void menu() throws Exception {
		
		Scanner sc = new Scanner(System.in);

		String st = null;
		int choice;
		do {
			System.out.println("----- Chào mừng bạn đến với chương trình quản lí bài tập của THINHNV30 -----");
			System.out.println("-----CHÚC BẠN NGÀY MỚI TỐT LÀNH-------");
			System.out.println(" 1.Chức năng Input Data----- ");
			System.out.println(" 2.Giới thiệu thông tin của các hành khách hiện có ");
			System.out.println(" 3.Cập nhật ngày khởi hành ");
			System.out.println(" 4.Sắp xếp danh sách hành khách ");
			System.out.println(" 5.Xóa những hành khách chưa tiến hành test covid ");
			System.out.println("======Nhập vào số nguyên tương ứng với bài tập bạn muốn chạy :");
			System.out.println();
			
			
			while (true) {
				try {
					st = sc.nextLine();
					choice = Integer.parseInt(st);
					System.out.println("Số bạn nhập là : " + choice);
					break;
				} catch (Exception ex) {
					System.out.println(st + "Không phải số nguyên");
					System.out.println("Vui lòng nhập lại ");
				}
			}
			switch (choice) {
			case 1:// Cau 1
					Methods_HanhKhach.insert();
				
				break;

			case 2:// Cau 2
				Methods_HanhKhach.getInformation();
				break;

			case 3:// Cau 3
				Methods_HanhKhach.updateFlighDate();
				break;
				
			case 4:// Cau 4
				Methods_HanhKhach.sortHanhKhach();
				break;
				
			case 5:// Cau 5
				Methods_HanhKhach.deleteHK();
				break;
				
			case 0:
				// 0. Thoát
				System.out.println("====Kết thúc chương trình======");
				break;

			default:
				System.out.println("Lựa chọn không hợp lệ");
				break;
			}
		} while (choice != 0);
		
	}
	
	
	public static void main(String[] args) {
		
		try {
			menu();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Program have an unexpected error occurred !!!");
					
			System.out.println("Program is Exit");

		}
	}
	
	
}
