package exceptions;

public class NoSuchAgeException extends Exception  {
	public NoSuchAgeException(String errMsg,int age) {
		 super(errMsg);
		 System.out.println("Add profile failed.");
		 System.out.println("Error message is: Invalid Age");
	 }	
}
