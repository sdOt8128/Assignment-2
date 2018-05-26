package exceptions;

public class TooYoungException extends Exception {
	
	public TooYoungException(String errMsg)
	{
		super(errMsg);
		System.out.println("Cannot connect Adult with a Minor");
	}
}
