package exceptions;

public class NotToBeClassmatesException extends Exception  {
	public NotToBeClassmatesException(String errMsg) {
		 super(errMsg);
		 System.out.println("Young child cannot be connected as classmate");
	 }
}
