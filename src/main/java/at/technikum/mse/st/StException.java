package at.technikum.mse.st;

public class StException extends Exception {
	private static final long serialVersionUID = 5196609971878813133L;

	public StException(String message) {
		super(message);
	}

	public StException(String message, Exception e) {
		super(message, e);
	}
}
