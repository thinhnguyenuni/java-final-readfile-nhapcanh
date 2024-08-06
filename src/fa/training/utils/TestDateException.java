package fa.training.utils;

public class TestDateException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TestDateException(){
		
	}
	public String toString(){ 
	    return "Nhập ngày sinh không đúng yêu cầu, vui lòng nhập lại : ";
	}
}
