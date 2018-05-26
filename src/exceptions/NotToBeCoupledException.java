package exceptions;

public class NotToBeCoupledException extends Exception  {
	public NotToBeCoupledException(String errMsg) {
		 super(errMsg);
		 System.out.println("Cannot connect Adult and Child as couple");
	 }
}
