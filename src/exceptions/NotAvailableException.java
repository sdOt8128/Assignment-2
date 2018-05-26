package exceptions;

public class NotAvailableException extends Exception  {
	public NotAvailableException(String errMsg) {
		 super(errMsg);
		 System.out.println("Cannot connect as couple, both adults must be single");
	 }
}
