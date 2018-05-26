package exceptions;

public class NoParentException extends Exception {
	public NoParentException(String errMsg) {
		super(errMsg);
		System.out.println("");
	}
}
