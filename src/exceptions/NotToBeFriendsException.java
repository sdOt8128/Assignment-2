package exceptions;

public class NotToBeFriendsException extends Exception {
	 public NotToBeFriendsException(String errMsg) {
		 super(errMsg);
		 System.out.println("Cannot connect, age difference > 3 years.");
	 }
}
