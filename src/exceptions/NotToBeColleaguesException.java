package exceptions;

public class NotToBeColleaguesException extends Exception {
	public NotToBeColleaguesException(String errMsg) {
		 super(errMsg);
		 System.out.println("Adult and Child cannot be connected as colleagues");
	 }
}
